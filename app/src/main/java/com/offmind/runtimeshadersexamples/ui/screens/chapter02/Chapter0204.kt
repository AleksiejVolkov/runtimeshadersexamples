package com.offmind.runtimeshadersexamples.ui.screens.chapter02

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.offmind.runtimeshadersexamples.R
import com.offmind.runtimeshadersexamples.ui.screens.provideTimeAsState
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun Chapter0204(
    codeContainer: @Composable ColumnScope.(String) -> Unit = {},
) {
    var percentage by remember {
        mutableFloatStateOf(0.0f)
    }
    var pointerPos by remember { mutableStateOf(Offset.Zero) }
    var reset by remember { mutableStateOf(0f) }

    LaunchedEffect(reset) {
        percentage = 0f
        // loop until we're virtually at 1f
        while (percentage < 0.995f) {
            // move 3% of the way from current value toward 1f
            percentage = lerp(percentage, 1f, 0.03f)
            delay(10)
        }
        // snap to 1f to finish
        percentage = 1f
    }

    Column(modifier = Modifier.fillMaxSize()
        .pointerInput(Unit) {
            awaitPointerEventScope {
                while (true) {
                    val position = awaitFirstDown().position
                    pointerPos = position
                    percentage = 0f
                    reset += 1f
                }
            }
        },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShaderView(
            modifier = Modifier.fillMaxWidth().weight(1f),
            animStage = percentage,
            pointerPos = pointerPos,
        ) {
            Image(modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.background_dark else R.drawable.background),
                contentDescription = null,
            )
            Image(
                modifier = Modifier.size(300.dp).clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop,
                painter = painterResource(id = R.drawable.android_mascote),
                contentDescription = null,
            )
        }
        Column(modifier = Modifier.fillMaxWidth().weight(0.5f)) {
            codeContainer(
                runtimeShader
            )
        }
    }
}

@Composable
private fun ShaderView(
    modifier: Modifier = Modifier,
    animStage: Float = 0f,
    pointerPos: Offset = Offset.Zero,
    content: @Composable () -> Unit,
) {
    val shader by remember {
        mutableStateOf(RuntimeShader(runtimeShader))
    }

    Box(
        modifier = modifier
            .onSizeChanged { size ->
                shader.setFloatUniform(
                    "resolution",
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .graphicsLayer {
                shader.setFloatUniform("percentage", animStage)
                shader.setFloatUniform("pointer", pointerPos.x, pointerPos.y)
                this.renderEffect = RenderEffect
                    .createRuntimeShaderEffect(shader, "image")
                    .asComposeRenderEffect()
            },
        contentAlignment = Alignment.Center) {
        content()
    }
}

private val runtimeShader = """
    uniform shader image;
    uniform vec2 resolution;
    uniform float percentage;
    uniform vec2 pointer;

     vec4 GetImageTexture(vec2 p, vec2 pivot, vec2 r) {
        if (r.x > r.y) {
            p.x /= r.x / r.y;
        } else {
            p.y /= r.y / r.x;
        }
        p += pivot;
        p *= r;
        return image.eval(p);
    }  

    float CircleSDF(vec2 p, float r) {
        return length(p) - r;
    }   

    vec3 ChromaticAberration(vec2 p, vec2 d, vec2 r) {
        vec2 center = vec2(0.5, 0.5);

        vec3 imageR = GetImageTexture(p, center + d, r).rgb;
        vec3 imageG = GetImageTexture(p, center, r).rgb;
        vec3 imageB = GetImageTexture(p, center - d, r).rgb;

        return vec3(imageR.r, imageG.g, imageB.b);
    }

    vec2 NormalizeCoordinates(vec2 o, vec2 r) {
        // Ensure coordinates are properly normalized to the -0.5 to 0.5 range
        float2 uv = o / r - 0.5;
        // Adjust for aspect ratio
        if (r.x > r.y) {
            uv.x *= r.x / r.y;
        } else {
            uv.y *= r.y / r.x;
        } 
        return uv;
    }

    vec4 Drop(vec2 d, vec2 p) {
        float coef = percentage;
        float r = 0.17 * coef;
        float sdf = CircleSDF(d - p, r);
        float mask = smoothstep(r + 0.1, r, sdf) * smoothstep(r - 0.1, r, sdf);

        vec4 image = GetImageTexture(d, vec2(0.5, 0.5), resolution);
        vec3 ripple = ChromaticAberration(d+sdf * mask, vec2(0.1, 0.0), resolution);

        // Combine the channels with the chromatic aberration
        return vec4(mix(image.rgb, ripple, mask*(1.-coef)),mask*(1.-coef));
    }

    vec4 main(float2 fragCoord) {
        // Normalize the fragment coordinates
        float2 uv = NormalizeCoordinates(fragCoord, resolution);
        // Normalize the pointer coordinates the same way
        float2 normalizedPointer = NormalizeCoordinates(pointer, resolution);

        // Apply the drop effect
        vec4 drop = Drop(uv, normalizedPointer);

        // Get the original image
        vec4 image = GetImageTexture(uv, vec2(0.5, 0.5), resolution);

        // Mix the original image with the drop effect
        vec3 finalColor = mix(image.rgb, drop.rgb, drop.a);

        return vec4(finalColor, image.a);
    }
""".trimIndent()

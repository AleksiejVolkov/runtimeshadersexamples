package com.offmind.runtimeshadersexamples.ui.screens.chapter02

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.offmind.runtimeshadersexamples.R
import com.offmind.runtimeshadersexamples.ui.screens.provideTimeAsState
import kotlin.random.Random

@Composable
fun Chapter0203(
    codeContainer: @Composable ColumnScope.(String) -> Unit = {},
) {
    var percentage by remember {
        mutableFloatStateOf(0.0f)
    }

    val percentageAnim = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(
            durationMillis = 100,
            delayMillis = 0,
        ),
        label = "",
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        ShaderView(
            modifier = Modifier
                .size(300.dp)
                .clickable(
                    indication = null, // Removes the ripple effect
                    interactionSource = remember { MutableInteractionSource() } // Prevents interaction tracking
                ) {
                    percentage = if (percentage == 1f) 0f else 1f
                },
            content = {
                Image(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.android_mascote_bw),
                    contentDescription = null,
                )
            },
            animStage = percentageAnim.value
        )
        Spacer(modifier = Modifier.weight(1f))
        codeContainer(
            runtimeShader
        )
    }
}

@Composable
private fun ShaderView(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    animStage: Float = 0f
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
                this.renderEffect = RenderEffect
                    .createRuntimeShaderEffect(shader, "image")
                    .asComposeRenderEffect()
            }) {
        content()
    }
}

private val runtimeShader = """
    uniform shader image;
    uniform vec2 resolution;
    uniform float percentage;
    
    vec4 GetImageTexture(vec2 p, vec2 pivot) {
        p.y /= resolution.y / resolution.x;
        p += pivot;
        p *= resolution;
        return image.eval(p);
    }  
     
    vec4 main(float2 fragCoord) {
        // Normalize fragment coordinates
        vec2 uv = fragCoord / resolution;

        float aberrationAmount = 0.009 * percentage;
        vec4 outColor;
        outColor.r = GetImageTexture(uv + vec2(aberrationAmount, 0.0), vec2(0.0, 0.0)).r;
        outColor.ga = GetImageTexture(uv, vec2(0.0, 0.0)).ga;
        outColor.b = GetImageTexture(uv - vec2(aberrationAmount, 0.0), vec2(0.0, 0.0)).b;
        
        return vec4(outColor.rgb, outColor.a);
    }
""".trimIndent()

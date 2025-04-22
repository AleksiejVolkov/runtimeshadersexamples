package com.offmind.runtimeshadersexamples.ui.screens.chapter02

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.sp
import com.offmind.runtimeshadersexamples.ui.screens.provideTimeAsState
import kotlin.random.Random

@Composable
fun Chapter0201(
    codeContainer: @Composable ColumnScope.(String) -> Unit = {},
) {
    var percentage by remember {
        mutableFloatStateOf(0.0f)
    }

    val percentageAnim = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(
            durationMillis = 1000,
            delayMillis = 0
        ),
        label = ""
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        ShaderView(
            modifier = Modifier
                .clipToBounds()
                .clickable(
                    indication = null, // Removes the ripple effect
                    interactionSource = remember { MutableInteractionSource() } // Prevents interaction tracking
                ) {
                    percentage = if (percentage == 0.0f) 1.0f else 0.0f
                },
            content = {
                Text(
                    text = "Hello, World!",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 24.sp
                )
            },
            percentage = percentageAnim.value
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
    percentage: Float = 0.5f,
    content: @Composable () -> Unit
) {
    val shader by remember {
        mutableStateOf(RuntimeShader(runtimeShader))
    }
    val time by provideTimeAsState(Random.nextFloat() * 10f)

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
                shader.setFloatUniform("time", time)
                shader.setFloatUniform("percentage", percentage)
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
    uniform float time;
    uniform float percentage;

    float Hash21(vec2 p) { 
        return fract(sin(dot(p, vec2(12.9898, 78.233))) * 43758.5453);
    }
        
    vec4 main(float2 fragCoord) {
         vec4 parent = image.eval(fragCoord);
         vec2 uv = fragCoord / resolution - 0.5;
         vec2 shiftedUv = uv + time*uv;
             
         vec3 noise = vec3(Hash21(shiftedUv)); 
         vec3 image = parent.rgb;
            
         vec3 col = mix(noise, image, percentage);
         float a = mix(1.0, parent.a, percentage);
                
         return vec4(col, a);
    }
""".trimIndent()

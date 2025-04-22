package com.offmind.runtimeshadersexamples.ui.screens.chapter01

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun Chapter0102(
    codeContainer: @Composable ColumnScope.(String) -> Unit = {},
) {
    val shader = remember { RuntimeShader(runtimeShader) }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .size(200.dp)
                .clipToBounds()
                .onSizeChanged { size ->
                    shader.setFloatUniform(
                        "resolution", size.width.toFloat(), size.height.toFloat()
                    )
                }
                .graphicsLayer {
                    this.renderEffect = RenderEffect
                        .createRuntimeShaderEffect(shader, "image")
                        .asComposeRenderEffect()
                }
                .background(Color.White)
        )
        Spacer(modifier = Modifier.weight(1f))
        codeContainer(
            runtimeShader
        )
    }
}

private val runtimeShader = """
    uniform shader image;
    uniform float2 resolution;

    half4 main(float2 fragCoord) {
        float2 uv = fragCoord / resolution - 0.5;
    
        float radius = 0.5;
        float3 circleColor = float3(0.632, 0.23, 0.56);
        float circle = step(length(uv), radius);
        
        float3 col = circle*circleColor;
            
        return float4(col, circle);
    }
""".trimIndent()
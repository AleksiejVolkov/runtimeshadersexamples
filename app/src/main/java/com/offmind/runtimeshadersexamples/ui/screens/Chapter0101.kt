package com.offmind.runtimeshadersexamples.ui.screens

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun Chapter0101() {
    val shader = remember { RuntimeShader(runtimeShader) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
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
    }
}

private val runtimeShader = """
    uniform shader image;
    uniform float2 resolution;

    half4 main(float2 fragCoord) {
        float2 uv = fragCoord / resolution;
        return half4(uv.x, uv.y, 0.0, 1.0);
    }
""".trimIndent()
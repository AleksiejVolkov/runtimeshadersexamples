package com.offmind.runtimeshadersexamples.ui.screens.chapter01

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
fun Chapter0101(
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
        float2 uv = fragCoord / resolution;
        return half4(uv.x, uv.y, 0.0, 1.0);
    }
""".trimIndent()

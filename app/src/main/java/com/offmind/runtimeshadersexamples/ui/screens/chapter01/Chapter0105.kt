package com.offmind.runtimeshadersexamples.ui.screens.chapter01

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp
import com.offmind.runtimeshadersexamples.ui.screens.provideTimeAsState

@Composable
fun Chapter0105(
    codeContainer: @Composable ColumnScope.(String) -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            ShadedBox()
            Text(text = "Loading...", color = Color.White, modifier = Modifier.padding(16.dp))
        }

        Spacer(modifier = Modifier.weight(1f))
        codeContainer(
            runtimeShader
        )
    }
}

@Composable
private fun ShadedBox() {
    val shader = remember { RuntimeShader(runtimeShader) }
    val time by provideTimeAsState()
    Box(modifier = Modifier
        .size(200.dp)
        .onSizeChanged { size ->
            shader.setFloatUniform(
                "resolution",
                size.width.toFloat(),
                size.height.toFloat()
            )
        }
        .graphicsLayer {
            shader.setFloatUniform("time", time)
            this.renderEffect = RenderEffect
                .createRuntimeShaderEffect(
                    shader, "image"
                )
                .asComposeRenderEffect()
        }
        .background(Color.Black)
    )
}

private val runtimeShader = """
    uniform shader image;
    uniform vec2 resolution;
    uniform float time;
    uniform float percentage;
    
    float sdCircle(vec2 p, float r) {
        return length(p) - r;
    }
   
    float DeformedCircle(float2 uv, float r, float offset) {
     float angle = atan(uv.y, uv.x);
     float d = sdCircle(uv, r);
     float ring = d + 0.01 * sin(2.*(angle+time+offset));
     return smoothstep(0.025, 0.02, abs(ring));
    }
    

    vec4 main(float2 fragCoord) {
     float2 uv = fragCoord / resolution - 0.5;
           
     float circle01 = DeformedCircle(uv, 0.405, 0.1);
     float circle02 = DeformedCircle(uv, 0.4, 0.3);
     float circle03 = DeformedCircle(uv, 0.401, 0.5);
          
     float a = clamp(circle01 + circle02 + circle03, 0.0, 1.0);
     vec3 col = circle01*vec3(1.0, 0.0, 0.0)+
                circle02*vec3(0.0, 1.0, 0.0)+
                circle03*vec3(0.0, 0.0, 1.0);
     return vec4(col*a, a);
     }

""".trimIndent()
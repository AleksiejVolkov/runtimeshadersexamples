package com.offmind.runtimeshadersexamples.ui.screens.chapter01

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
fun Chapter0104(
    codeContainer: @Composable ColumnScope.(String) -> Unit = {},
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        GlowButton(text = "Hello, glow!")
        Spacer(modifier = Modifier.weight(1f))
        codeContainer(
            runtimeShader
        )
    }
}

@Composable
private fun GlowButton(
    modifier: Modifier = Modifier,
    text: String
) {
    val shader by remember {
        mutableStateOf(RuntimeShader(runtimeShader))
    }
    val time = provideTimeAsState()

    OutlinedButton(
        border = BorderStroke(0.dp, Color.Transparent),
        modifier = modifier
            .onSizeChanged { size ->
                shader.setFloatUniform(
                    "resolution",
                    size.width.toFloat(),
                    size.height.toFloat()
                )
            }
            .graphicsLayer {
                shader.setFloatUniform("time", time.value)
                this.renderEffect = RenderEffect
                    .createRuntimeShaderEffect(shader, "image")
                    .asComposeRenderEffect()
            },
        onClick = { }) {
        Text(
            text,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}


private val runtimeShader = """
    uniform shader image;
    uniform float2 resolution;
    uniform float time;
    
    float sdRoundedBox( in vec2 p, in vec2 b, in vec4 r ) {
        r.xy = (p.x>0.0) ? r.xy : r.zw;
        r.x  = (p.y>0.0) ? r.x  : r.y;
        vec2 q = abs(p)-b + r.x;
        return min(max(q.x,q.y), 0.0) 
                             + length(max(q,0.0)) - r.x;
    }
    
    float4 main(float2 fragCoord) {
        float2 uv = fragCoord / resolution - 0.5;
        float ratio = resolution.x / resolution.y;
        uv.x *=ratio; 
        
        vec4 img = image.eval(fragCoord).rgba;  
        vec3 grad = 0.5 + vec3(0.5 * sin(time + uv.x + 0), 
                    0.5 * cos(time + uv.y + 2), 
                    0.5 * sin(time + uv.x*uv.y + 4));
            
        vec2 d = vec2(0.5 * ratio, 0.4);
        vec4 r = vec4(0.4);
        
        float shadow = clamp(1./(25.*sdRoundedBox(uv,d,r)), 0., 1.);
        float shape = clamp(shadow-img.a,0.,1.);
        
        vec3 col = mix(img.rgb, grad, shape);
        
        return float4(col,shape);
    }
""".trimIndent()
package com.offmind.runtimeshadersexamples.ui.screens.chapter02

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.offmind.runtimeshadersexamples.R

@Composable
fun Chapter0205(
    codeContainer: @Composable ColumnScope.(String) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShaderView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .weight(1f)
        ) {
            val pagerState = rememberPagerState(pageCount = { 30 })
            var pagerHeightPx by remember { mutableFloatStateOf(0f) }
            val itemHeight = 70.dp

            val fling = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(10)
            )

            VerticalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .onGloballyPositioned { coordinates ->
                        pagerHeightPx = coordinates.size.height.toFloat()
                    },
                contentPadding = PaddingValues(vertical = with(LocalDensity.current) {
                    val raw = pagerHeightPx - itemHeight.toPx()
                    val padPx = (raw / 2f).coerceAtLeast(0f)
                    padPx.toDp()
                }),
                flingBehavior = fling,
            ) { page ->
                SampleItem(
                    modifier = Modifier.height(itemHeight),
                    title = "item number $page",
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            codeContainer(
                runtimeShader
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SampleItem(
    modifier: Modifier,
    title: String) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ShaderView(
    modifier: Modifier = Modifier,
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
                this.renderEffect = RenderEffect
                    .createRuntimeShaderEffect(shader, "image")
                    .asComposeRenderEffect()
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}

private val runtimeShader = """
    uniform shader image;
    uniform vec2 resolution;
   
     vec4 GetImageTexture(vec2 p, vec2 pivot, vec2 r) {
        p.x /= r.x / r.y;
        p += pivot;
        p *= r;
        return image.eval(p);
    }  

    vec2 NormalizeCoordinates(vec2 o, vec2 r) {
        float2 uv = o / r - 0.5;
        uv.x *= r.x / r.y;
       
        return uv;
    }
    
    float ExponentialStep(float edge0, float edge1, float x, float exponent) {
        x = clamp((x - edge0) / (edge1 - edge0), 0.0, 1.0);
        return pow(x, exponent);
    }

    vec4 main(float2 fragCoord) {
        float2 uv = NormalizeCoordinates(fragCoord, resolution);

        float scaleX = smoothstep(0.35, 0.1, length(uv.y));
        float xDistort = (1.0-scaleX)+1.0;
        vec4 content = GetImageTexture(uv*(vec2(xDistort,1.0)), vec2(0.5, 0.5), resolution);
        
        float alpha = ExponentialStep(0.3, 0.0, length(uv.y), 3.0);
   
        return half4(content.rgb*alpha*content.a,alpha*content.a);
    }
""".trimIndent()

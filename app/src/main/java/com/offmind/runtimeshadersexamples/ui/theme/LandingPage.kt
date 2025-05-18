package com.offmind.runtimeshadersexamples.ui.theme

import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.offmind.runtimeshadersexamples.model.Project
import com.offmind.runtimeshadersexamples.ui.chapters01
import com.offmind.runtimeshadersexamples.ui.chapters02
import org.koin.compose.koinInject
import androidx.core.net.toUri

/**
 * Data class representing a chapter item in the landing page
 */
data class ChapterInfo(
    val title: String,
    val description: String,
    val route: String,
    val composable: (@Composable ColumnScope.(String) -> Unit) -> @Composable () -> Unit = { codeContainer ->
        {
            // This will be resolved at runtime based on the route
            // The actual implementation is in AppNavigation.kt
        }
    }
)

@Composable
fun LandingPage(
    innerPadding: PaddingValues,
    onNavigateToChapter: (String) -> Unit = {}
) {
    val project: Project = koinInject()
    val fadeShader = remember { RuntimeShader(runtimeShader) }

    Column(Modifier.padding(innerPadding)) {
        ProjectInfo(
            project = project,
        )
        LazyColumn(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .onSizeChanged { size ->
                    fadeShader.setFloatUniform(
                        "resolution", size.width.toFloat(), size.height.toFloat()
                    )
                }
                .graphicsLayer {
                    this.renderEffect = RenderEffect
                        .createRuntimeShaderEffect(fadeShader, "image")
                        .asComposeRenderEffect()
                }
        ) {
            item {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Chapter 01", style = MaterialTheme.typography.titleLarge)
                    Text("Circles and all you need about that", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(chapters01) { chapter ->
                ChapterItem(
                    chapter = chapter,
                    onClick = { onNavigateToChapter(chapter.route) }
                )
            }
            item {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text("Chapter 02", style = MaterialTheme.typography.titleLarge)
                    Text("Blending with the input", style = MaterialTheme.typography.titleMedium)
                }
            }
            items(chapters02) { chapter ->
                ChapterItem(
                    chapter = chapter,
                    onClick = { onNavigateToChapter(chapter.route) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterItem(
    chapter: ChapterInfo,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = chapter.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = chapter.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ProjectInfo(project: Project, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = project.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Version: ${project.version}",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = project.description,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
        Row {
            Text(
                text = "You can buy the book ",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium
            )
            val link = project.link
            val annotatedString = buildAnnotatedString {
                pushStringAnnotation(tag = "URL", annotation = link)
                withStyle(
                    style = MaterialTheme.typography.bodyMedium.toSpanStyle()
                        .copy(color = MaterialTheme.colorScheme.primary)
                ) {
                    append("here")
                }
                pop()
            }
            val context = LocalContext.current
            ClickableText(
                text = annotatedString,
                style = MaterialTheme.typography.bodyMedium,
                onClick = { offset ->
                    annotatedString.getStringAnnotations(tag = "URL", start = offset, end = offset)
                        .firstOrNull()?.let { annotation ->
                            val intent = Intent(Intent.ACTION_VIEW, annotation.item.toUri())
                            context.startActivity(intent)
                        }
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun ProjectInfoPreview() {
    RuntimeShadersExamplesTheme {
        ProjectInfo(
            project = Project(
                name = "Preview Project",
                version = "1.0.0",
                description = "This is a preview of the project info"
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RuntimeShadersExamplesTheme {
        Greeting("Android")
    }
}

private val runtimeShader = """
    uniform shader image;
    uniform float2 resolution;

    half4 main(float2 fragCoord) {
       float2 uv = fragCoord / resolution - 0.5;
    
       vec4 content = image.eval(fragCoord).rgba;
       
       float alpha = smoothstep(0.5, 0.48, length(uv.y)) * step(length(uv.x), 0.5);

       return half4(content.rgb*alpha*content.a,alpha*content.a);
    }
""".trimIndent()

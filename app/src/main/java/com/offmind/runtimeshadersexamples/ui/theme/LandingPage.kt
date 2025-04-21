package com.offmind.runtimeshadersexamples.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.offmind.runtimeshadersexamples.model.Project
import com.offmind.runtimeshadersexamples.navigation.NavRoutes
import org.koin.compose.koinInject

/**
 * Data class representing a chapter item in the landing page
 */
data class ChapterInfo(
    val title: String,
    val description: String,
    val route: String
)

// List of chapters
val chapters = listOf(
    ChapterInfo(
        title = "Section 01: Basic Shader",
        description = "A simple shader example showing color gradients",
        route = NavRoutes.CHAPTER_0101
    ),
    ChapterInfo(
        title = "Section 02: Step function",
        description = "A simple shader example showing circle",
        route = NavRoutes.CHAPTER_0102
    ),
    ChapterInfo(
        title = "Section 03: Smoothstep",
        description = "An example of smoothstep function",
        route = NavRoutes.CHAPTER_0103
    ),
    ChapterInfo(
        title = "Section 04: Signed Distance Function",
        description = "Button glow with time effect",
        route = NavRoutes.CHAPTER_0104
    ),
    ChapterInfo(
        title = "Section 05: Angle to the point",
        description = "Three deformed circles example",
        route = NavRoutes.CHAPTER_0105
    ),
    ChapterInfo(
        title = "Section 06: Combining angle and smoothstep",
        description = "Three circles into one effect",
        route = NavRoutes.CHAPTER_0106
    )
)

@Composable
fun LandingPage(
    innerPadding: PaddingValues,
    onNavigateToChapter: (String) -> Unit = {}
) {
    val project: Project = koinInject()

    Column {
        ProjectInfo(
            project = project,
            modifier = Modifier.padding(innerPadding)
        )
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            item {
                Text("Chapter 01", style = MaterialTheme.typography.titleLarge)
            }
            items(chapters) { chapter ->
                ChapterItem(
                    chapter = chapter,
                    onClick = { onNavigateToChapter(chapter.route) }
                )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    ChapterItem(
        chapter = ChapterInfo(
            title = title,
            description = description,
            route = "" // Route not needed for this overload
        ),
        onClick = onClick
    )
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

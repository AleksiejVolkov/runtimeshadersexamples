package com.offmind.runtimeshadersexamples.ui.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.offmind.runtimeshadersexamples.model.Project
import org.koin.compose.koinInject

@Composable
fun LandingPage(
    innerPadding: PaddingValues,
    onNavigateToChapter0101: () -> Unit = {}
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
                ChapterItem(
                    title = "Chapter 01-01: Basic Shader",
                    description = "A simple shader example showing color gradients",
                    onClick = onNavigateToChapter0101
                )
            }
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = description,
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

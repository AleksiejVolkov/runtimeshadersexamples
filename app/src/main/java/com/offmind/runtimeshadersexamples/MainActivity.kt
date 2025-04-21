package com.offmind.runtimeshadersexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.offmind.runtimeshadersexamples.model.Project
import com.offmind.runtimeshadersexamples.ui.theme.RuntimeShadersExamplesTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {
    // Inject Project dependency
    private val project: Project by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuntimeShadersExamplesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProjectInfo(
                        project = project,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectInfo(project: Project, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text(
            text = project.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Version: ${project.version}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = project.description,
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

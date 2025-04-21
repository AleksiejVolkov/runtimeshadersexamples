package com.offmind.runtimeshadersexamples.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.jeziellago.compose.markdowntext.MarkdownText

@Composable
fun ColumnScope.CodeContainer(
    runtimeShader: String
) {
    Box(
        modifier = Modifier
            .weight(1.2f)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        val scrollState = rememberScrollState()
        MarkdownText(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(bottom = 20.dp)
                .clip(MaterialTheme.shapes.large),
            markdown = "```GLSL\n${runtimeShader}\n```",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp
        )
    }
}

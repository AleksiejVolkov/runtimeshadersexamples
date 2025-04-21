package com.offmind.runtimeshadersexamples.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.State
import kotlinx.coroutines.delay
import kotlinx.coroutines.delay

@Composable
fun provideTimeAsState(initialValue: Float = 0f): State<Float> {
    val timeState = remember { mutableFloatStateOf(initialValue) }

    LaunchedEffect(Unit) {
        while (true) {
            timeState.floatValue += 0.01f
            delay(10)
        }
    }


    return timeState
}

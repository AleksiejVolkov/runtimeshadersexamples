package com.offmind.runtimeshadersexamples.ui

import com.offmind.runtimeshadersexamples.navigation.NavRoutes
import com.offmind.runtimeshadersexamples.ui.theme.ChapterInfo


// List of chapters
val chapters01 = listOf(
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

// List of chapters
val chapters02 = listOf(
    ChapterInfo(
        title = "Section 02: The very basic noise function",
        description = "A noise to text effect",
        route = NavRoutes.CHAPTER_0101
    ),
)
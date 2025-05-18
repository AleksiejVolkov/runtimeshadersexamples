package com.offmind.runtimeshadersexamples.ui

import com.offmind.runtimeshadersexamples.navigation.NavRoutes
import com.offmind.runtimeshadersexamples.ui.screens.chapter01.Chapter0101
import com.offmind.runtimeshadersexamples.ui.screens.chapter01.Chapter0102
import com.offmind.runtimeshadersexamples.ui.screens.chapter01.Chapter0103
import com.offmind.runtimeshadersexamples.ui.screens.chapter01.Chapter0104
import com.offmind.runtimeshadersexamples.ui.screens.chapter01.Chapter0105
import com.offmind.runtimeshadersexamples.ui.screens.chapter01.Chapter0106
import com.offmind.runtimeshadersexamples.ui.screens.chapter02.Chapter0201
import com.offmind.runtimeshadersexamples.ui.screens.chapter02.Chapter0202
import com.offmind.runtimeshadersexamples.ui.screens.chapter02.Chapter0203
import com.offmind.runtimeshadersexamples.ui.screens.chapter02.Chapter0204
import com.offmind.runtimeshadersexamples.ui.screens.chapter02.Chapter0205
import com.offmind.runtimeshadersexamples.ui.theme.ChapterInfo

/**
 * This file contains the definitions of all chapters in the application.
 * 
 * To add a new shader screen:
 * 1. Create a new Chapter composable in the ui.screens package (e.g., Chapter0203.kt)
 * 2. Import it at the top of this file
 * 3. Add a new ChapterInfo to the appropriate chapter list (e.g., chapters02)
 *    - Use NavRoutes.getChapterRoute("0203") to generate the route
 *    - Provide the composable reference: { codeContainer -> { Chapter0203(codeContainer = codeContainer) } }
 * 
 * That's it! The navigation system will automatically handle the new chapter.
 * No need to update NavRoutes or AppNavigation.kt manually.
 */


// List of chapters
val chapters01 = listOf(
    ChapterInfo(
        title = "Section 01: Basic Shader",
        description = "A simple shader example showing color gradients",
        route = NavRoutes.getChapterRoute("0101"),
        composable = { codeContainer -> { Chapter0101(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 02: Step function",
        description = "A simple shader example showing circle",
        route = NavRoutes.getChapterRoute("0102"),
        composable = { codeContainer -> { Chapter0102(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 03: Smoothstep",
        description = "An example of smoothstep function",
        route = NavRoutes.getChapterRoute("0103"),
        composable = { codeContainer -> { Chapter0103(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 04: Signed Distance Function",
        description = "Button glow with time effect",
        route = NavRoutes.getChapterRoute("0104"),
        composable = { codeContainer -> { Chapter0104(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 05: Angle to the point",
        description = "Three deformed circles example",
        route = NavRoutes.getChapterRoute("0105"),
        composable = { codeContainer -> { Chapter0105(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 06: Combining angle and smoothstep",
        description = "Three circles into one effect",
        route = NavRoutes.getChapterRoute("0106"),
        composable = { codeContainer -> { Chapter0106(codeContainer = codeContainer) } }
    )
)

// List of chapters
val chapters02 = listOf(
    ChapterInfo(
        title = "Section 01: The very basic noise function",
        description = "A noise to text effect",
        route = NavRoutes.getChapterRoute("0201"),
        composable = { codeContainer -> { Chapter0201(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 02: Simplest Value noise",
        description = "Breaking image into pixels",
        route = NavRoutes.getChapterRoute("0202"),
        composable = { codeContainer -> { Chapter0202(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 03: Chromatic aberration",
        description = "Simple Chromatic aberration effect",
        route = NavRoutes.getChapterRoute("0203"),
        composable = { codeContainer -> { Chapter0203(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 04: Ripple effect",
        description = "Very basic shokewave",
        route = NavRoutes.getChapterRoute("0204"),
        composable = { codeContainer -> { Chapter0204(codeContainer = codeContainer) } }
    ),
    ChapterInfo(
        title = "Section 05: Fade list items",
        description = "Fade and scale list items on edges",
        route = NavRoutes.getChapterRoute("0205"),
        composable = { codeContainer -> { Chapter0205(codeContainer = codeContainer) } }
    ),
)
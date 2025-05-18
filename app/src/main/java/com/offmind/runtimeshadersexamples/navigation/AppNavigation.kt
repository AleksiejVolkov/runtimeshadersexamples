package com.offmind.runtimeshadersexamples.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.offmind.runtimeshadersexamples.ui.chapters01
import com.offmind.runtimeshadersexamples.ui.chapters02
import com.offmind.runtimeshadersexamples.ui.screens.CodeContainer
import com.offmind.runtimeshadersexamples.ui.theme.ChapterInfo
import com.offmind.runtimeshadersexamples.ui.theme.LandingPage

/**
 * Navigation routes for the application
 * 
 * This object provides route constants and helper functions for navigation.
 * 
 * When adding a new shader screen, you don't need to modify this file.
 * Instead, use the NavRoutes.getChapterRoute() function in Chapters.kt
 * to generate the route for your new chapter.
 * 
 * Example:
 * ```
 * ChapterInfo(
 *     title = "My New Chapter",
 *     description = "Description of my new chapter",
 *     route = NavRoutes.getChapterRoute("0203"),
 *     composable = { codeContainer -> { Chapter0203(codeContainer = codeContainer) } }
 * )
 * ```
 */
object NavRoutes {
    const val LANDING = "landing"

    /**
     * Helper function to generate route from chapter number
     * Example: "0101" -> "chapter_0101"
     */
    fun generateRoute(chapterNumber: String): String {
        return "chapter_$chapterNumber".lowercase()
    }

    /**
     * Helper function to get a route constant for a chapter
     * This can be used when adding new chapters
     * Example: getChapterRoute("0103") -> "chapter_0103"
     */
    fun getChapterRoute(chapterNumber: String): String {
        return generateRoute(chapterNumber)
    }
}

/**
 * Helper function to add chapter routes to NavGraphBuilder
 * 
 * This function automatically adds all chapters from the provided list to the navigation graph.
 * It uses the route and composable properties from each ChapterInfo to set up the navigation.
 * 
 * This is part of the generalized navigation system that makes it easy to add new shader screens
 * without having to manually update the navigation code.
 */
private fun NavGraphBuilder.addChapterRoutes(chapters: List<ChapterInfo>) {
    chapters.forEach { chapter ->
        composable(chapter.route) {
            chapter.composable { runtimeShader ->
                CodeContainer(
                    runtimeShader = runtimeShader
                )
            }()
        }
    }
}

/**
 * Main navigation component for the application
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    innerPadding: PaddingValues
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.LANDING
    ) {
        composable(NavRoutes.LANDING) {
            LandingPage(
                innerPadding = innerPadding,
                onNavigateToChapter = { route ->
                    navController.navigate(route)
                }
            )
        }

        // Add all chapter routes
        addChapterRoutes(chapters01)
        addChapterRoutes(chapters02)
    }
}

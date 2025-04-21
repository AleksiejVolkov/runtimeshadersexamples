package com.offmind.runtimeshadersexamples.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.offmind.runtimeshadersexamples.ui.screens.Chapter0101
import com.offmind.runtimeshadersexamples.ui.theme.LandingPage

/**
 * Navigation routes for the application
 */
object NavRoutes {
    const val LANDING = "landing"
    const val CHAPTER_0101 = "chapter_0101"
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
                onNavigateToChapter0101 = {
                    navController.navigate(NavRoutes.CHAPTER_0101)
                }
            )
        }
        
        composable(NavRoutes.CHAPTER_0101) {
            Chapter0101()
        }
    }
}
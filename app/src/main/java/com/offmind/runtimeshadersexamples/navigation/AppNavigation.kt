package com.offmind.runtimeshadersexamples.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.offmind.runtimeshadersexamples.ui.screens.Chapter0101
import com.offmind.runtimeshadersexamples.ui.screens.Chapter0102
import com.offmind.runtimeshadersexamples.ui.screens.Chapter0103
import com.offmind.runtimeshadersexamples.ui.screens.Chapter0104
import com.offmind.runtimeshadersexamples.ui.screens.Chapter0105
import com.offmind.runtimeshadersexamples.ui.screens.Chapter0106
import com.offmind.runtimeshadersexamples.ui.screens.CodeContainer
import com.offmind.runtimeshadersexamples.ui.theme.LandingPage

/**
 * Navigation routes for the application
 */
object NavRoutes {
    const val LANDING = "landing"
    const val CHAPTER_0101 = "chapter_0101"
    const val CHAPTER_0102 = "chapter_0102"
    const val CHAPTER_0103 = "chapter_0103"
    const val CHAPTER_0104 = "chapter_0104"
    const val CHAPTER_0105 = "chapter_0105"
    const val CHAPTER_0106 = "chapter_0106"
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

        composable(NavRoutes.CHAPTER_0101) {
            Chapter0101(
                codeContainer = { runtimeShader ->
                    CodeContainer(
                        runtimeShader = runtimeShader
                    )
                }
            )
        }

        composable(NavRoutes.CHAPTER_0102) {
            Chapter0102(
                codeContainer = { runtimeShader ->
                    CodeContainer(
                        runtimeShader = runtimeShader
                    )
                }
            )
        }

        composable(NavRoutes.CHAPTER_0103) {
            Chapter0103(
                codeContainer = { runtimeShader ->
                    CodeContainer(
                        runtimeShader = runtimeShader
                    )
                }
            )
        }

        composable(NavRoutes.CHAPTER_0104) {
            Chapter0104(
                codeContainer = { runtimeShader ->
                    CodeContainer(
                        runtimeShader = runtimeShader
                    )
                }
            )
        }

        composable(NavRoutes.CHAPTER_0105) {
            Chapter0105(
                codeContainer = { runtimeShader ->
                    CodeContainer(
                        runtimeShader = runtimeShader
                    )
                }
            )
        }

        composable(NavRoutes.CHAPTER_0106) {
            Chapter0106(
                codeContainer = { runtimeShader ->
                    CodeContainer(
                        runtimeShader = runtimeShader
                    )
                }
            )
        }
    }
}

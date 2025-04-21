package com.offmind.runtimeshadersexamples

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.rememberNavController
import com.offmind.runtimeshadersexamples.navigation.AppNavigation
import com.offmind.runtimeshadersexamples.ui.theme.RuntimeShadersExamplesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RuntimeShadersExamplesTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Image(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        contentDescription = null,
                        painter = painterResource(id = if(isSystemInDarkTheme()) R.drawable.background_dark else R.drawable.background)
                    )
                    AppNavigation(
                        navController = navController,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}

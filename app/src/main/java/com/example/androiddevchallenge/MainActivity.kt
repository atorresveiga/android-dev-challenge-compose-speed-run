/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.ui.HomeScreen
import com.example.androiddevchallenge.ui.LoginScreen
import com.example.androiddevchallenge.ui.WelcomeScreen
import com.example.androiddevchallenge.ui.theme.WeTradeTheme
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ProvideWindowInsets {
                WeTradeTheme {
                    WeTradeApp()
                }
            }
        }
    }
}

// Start building your app here!
@Composable
fun WeTradeApp() {
    Surface(color = MaterialTheme.colors.background) {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Screens.Welcome.route
        ) {
            SCREENS.forEach { screen ->
                composable(screen.route) { screen.content(navController) }
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {
    WeTradeTheme {
        WeTradeApp()
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    WeTradeTheme(darkTheme = true) {
        WeTradeApp()
    }
}

private val SCREENS =
    listOf(
        Screens.Welcome,
        Screens.Login,
        Screens.Home
    )

sealed class Screens(
    val route: String,
    val title: String,
    val content: @Composable (navController: NavController) -> Unit
) {
    object Welcome : Screens(
        "welcome",
        "Welcome",
        { navController -> WelcomeScreen(navController) }
    )

    object Home : Screens(
        "home",
        "Home",
        { navController -> HomeScreen(navController) }
    )

    object Login : Screens(
        "shop",
        "Shop",
        { navController -> LoginScreen(navController) }
    )
}

package com.esgi.inspiration

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun RootNavHost(default: String){
    val navController = rememberNavController()

    NavHost(navController, startDestination = default) {
        composable(
            route = "welcome",
        ) {
            WelcomeScreen(navController)
        }
        composable(
            route = "login",
        ) {
            LoginScreen(navController)
        }
    }
}

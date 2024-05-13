package com.esgi.inspiration

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esgi.inspiration.network.TracksRepository

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
        composable(
            route = "recommend"
        ) {
            RecommendScreen(TracksRepository())
        }
        composable(
            route = "choose"
        ) {
            ChooseScreen(navController)
        }
        composable(
            route = "likedSongs"
        ) {
            TopSongScreen(TracksRepository())
        }
    }
}

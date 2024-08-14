package com.example.knowledge.compose.jetweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.knowledge.compose.jetweatherforecast.screens.about.AboutScreen
import com.example.knowledge.compose.jetweatherforecast.screens.favorites.FavoritesScreen
import com.example.knowledge.compose.jetweatherforecast.screens.main.MainScreen
import com.example.knowledge.compose.jetweatherforecast.screens.main.MainViewModel
import com.example.knowledge.compose.jetweatherforecast.screens.search.SearchScreen
import com.example.knowledge.compose.jetweatherforecast.screens.settings.SettingsScreen
import com.example.knowledge.compose.jetweatherforecast.screens.splash.SplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = WeatherScreens.SplashScreen.name
    ) {
        composable(WeatherScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }

        // www.google.com/cityname="Seattle"
        val route = WeatherScreens.MainScreen.name
        composable(
            route = "$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                }
            )
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                MainScreen(
                    navController = navController,
                    mainViewModel,
                    city = city
                )
            }
        }

        composable(WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(WeatherScreens.FavoriteScreen.name) {
            FavoritesScreen(navController = navController)
        }

        composable(WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }
}
package com.example.knowledge.compose.jetreaderapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.knowledge.compose.jetreaderapp.screens.home.HomeScreen
import com.example.knowledge.compose.jetreaderapp.screens.login.LoginScreen
import com.example.knowledge.compose.jetreaderapp.screens.splash.ReaderSplashScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {
        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }
        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        composable(ReaderScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
    }
}
package com.example.knowledge.compose.jetreaderapp.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.knowledge.compose.jetreaderapp.screens.details.BookDetailsScreen
import com.example.knowledge.compose.jetreaderapp.screens.home.HomeScreen
import com.example.knowledge.compose.jetreaderapp.screens.home.ReaderHomeViewModel
import com.example.knowledge.compose.jetreaderapp.screens.login.LoginScreen
import com.example.knowledge.compose.jetreaderapp.screens.search.BookSearchScreen
import com.example.knowledge.compose.jetreaderapp.screens.search.ReaderBookSearchViewModel
import com.example.knowledge.compose.jetreaderapp.screens.splash.ReaderSplashScreen
import com.example.knowledge.compose.jetreaderapp.screens.stats.StatsScreen
import com.example.knowledge.compose.jetreaderapp.screens.update.BookUpdateScreen

@RequiresApi(Build.VERSION_CODES.N)
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
            val homeViewModel = hiltViewModel<ReaderHomeViewModel>()
            HomeScreen(navController = navController, homeViewModel)
        }
        composable(ReaderScreens.StatsScreen.name) {
            StatsScreen(navController = navController)
        }
        composable(ReaderScreens.SearchScreen.name) {
            val searchViewModel = hiltViewModel<ReaderBookSearchViewModel>()
            BookSearchScreen(navController = navController, viewModel = searchViewModel)
        }
        val detailName = ReaderScreens.DetailScreen.name
        composable(
            "$detailName/{bookId}",
            arguments = listOf(
                navArgument("bookId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("bookId").let {
                BookDetailsScreen(navController = navController, bookId = it.toString())
            }
        }
        val updateName = ReaderScreens.UpdateScreen.name
        composable(
            "$updateName/{bookItemId}",
            arguments = listOf(navArgument("bookItemId") {
                type = NavType.StringType
            })
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("bookItemId").let {
                BookUpdateScreen(navController = navController, bookItemId = it.toString())
            }
        }
    }
}
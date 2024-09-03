package com.example.knowledge.compose.jetreaderapp.navigation

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    StatsScreen;

    companion object {
        fun fromRoute(route: String): ReaderScreens =
            when (route?.substringBefore("/")) {
                SplashScreen.name -> SplashScreen
                LoginScreen.name -> LoginScreen
                CreateAccountScreen.name -> CreateAccountScreen
                HomeScreen.name -> HomeScreen
                SearchScreen.name -> SearchScreen
                DetailScreen.name -> DetailScreen
                UpdateScreen.name -> UpdateScreen
                StatsScreen.name -> StatsScreen
                null -> HomeScreen
                else -> throw IllegalStateException("Route $route is not recognize")
            }
    }

}
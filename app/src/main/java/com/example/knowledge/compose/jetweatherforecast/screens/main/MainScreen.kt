package com.example.knowledge.compose.jetweatherforecast.screens.main

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetweatherforecast.data.DataOrException
import com.example.knowledge.compose.jetweatherforecast.model.Weather
import com.example.knowledge.compose.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewmodel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewmodel.getWeatherData("Malaga")
    }.value
    if (weatherData.loading == true) {
        CircularProgressIndicator()
    } else if (weatherData.data != null) {
        MainScaffold(weather = weatherData.data!!, navController = navController)
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name}, ${weather.city.country}",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            elevation = 5.dp) {
            Logger.log(title = "Scaffold", message = "Button clicked")
        }
    }

    ) {
        MainContent(data = weather)
    }
}

@Composable
fun MainContent(data: Weather) {
    Text(text = data.city.name)

}
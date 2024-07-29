package com.example.knowledge.compose.jetweatherforecast.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetweatherforecast.data.DataOrException
import com.example.knowledge.compose.jetweatherforecast.model.Weather
import com.example.knowledge.compose.jetweatherforecast.utils.AppColors
import com.example.knowledge.compose.jetweatherforecast.utils.Constants.BASE_IMAGE_URL
import com.example.knowledge.compose.jetweatherforecast.utils.Constants.IMAGE_EXTENSION
import com.example.knowledge.compose.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun MainScreen(navController: NavController, mainViewmodel: MainViewModel = hiltViewModel()) {
    val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
        initialValue = DataOrException(loading = true)
    ) {
        value = mainViewmodel.getWeatherData("Moscow")
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
            elevation = 5.dp
        ) {
            Logger.log(title = "Scaffold", message = "Button clicked")
        }
    }

    ) {
        MainContent(data = weather)
    }
}

@Composable
fun MainContent(data: Weather) {
    val imageUrl = "$BASE_IMAGE_URL${data.list[0].weather[0].icon}$IMAGE_EXTENSION"
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Nov 29",
            style = MaterialTheme.typography.caption,
            color = AppColors.ColorSecondaryLight,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = AppColors.WeatherBackground
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = "56",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold,
                    color = AppColors.ColorPrimaryDark
                )
                Text(
                    text = "Show",
                    fontStyle = FontStyle.Italic,
                    color = AppColors.ColorAccent
                )
            }
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = "Icon image",
        modifier = Modifier.size(80.dp)
    )
}

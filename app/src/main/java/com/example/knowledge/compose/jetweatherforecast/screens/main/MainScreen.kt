package com.example.knowledge.compose.jetweatherforecast.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetweatherforecast.data.DataOrException
import com.example.knowledge.compose.jetweatherforecast.model.Weather
import com.example.knowledge.compose.jetweatherforecast.navigation.WeatherScreens
import com.example.knowledge.compose.jetweatherforecast.screens.settings.SettingsViewModel
import com.example.knowledge.compose.jetweatherforecast.utils.AppColors.ColorAccent
import com.example.knowledge.compose.jetweatherforecast.utils.AppColors.ColorPrimary
import com.example.knowledge.compose.jetweatherforecast.utils.AppColors.ColorPrimaryDark
import com.example.knowledge.compose.jetweatherforecast.utils.AppColors.WeatherBackground
import com.example.knowledge.compose.jetweatherforecast.utils.Constants.BASE_IMAGE_URL
import com.example.knowledge.compose.jetweatherforecast.utils.Constants.IMAGE_EXTENSION
import com.example.knowledge.compose.jetweatherforecast.utils.formatDate
import com.example.knowledge.compose.jetweatherforecast.utils.formatDecimals
import com.example.knowledge.compose.jetweatherforecast.widgets.HumidityWindPressureRow
import com.example.knowledge.compose.jetweatherforecast.widgets.SunsetSunRiseRow
import com.example.knowledge.compose.jetweatherforecast.widgets.WeatherAppBar
import com.example.knowledge.compose.jetweatherforecast.widgets.WeatherDetailRow
import com.example.knowledge.compose.jetweatherforecast.widgets.WeatherStateImage
import com.example.knowledge.compose.theme.ColorSecondaryLight

@Composable
fun MainScreen(
    navController: NavController,
    mainViewmodel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    Logger.log(title = "Main Screen", message = city.toString())
    val currentCity: String = if (city.isNullOrBlank()) "Malaga" else city
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewmodel.getWeatherData(currentCity.toString(), units = unit)
        }.value
        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData.data != null) {
            MainScaffold(
                weather = weatherData.data!!,
                navController = navController,
                isImperial = isImperial
            )
        }
    }
}

@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name}, ${weather.city.country}",
            navController = navController,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp
        ) {
            Logger.log(title = "Scaffold", message = "Button clicked")
        }
    }

    ) {
        MainContent(data = weather, isImperial = isImperial)
    }
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    val weatherItem = data.list[0]
    val imageUrl = "$BASE_IMAGE_URL${weatherItem.weather[0].icon}$IMAGE_EXTENSION"

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt), // Wed, Nov 30
            style = MaterialTheme.typography.body1,
            color = ColorSecondaryLight,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp)
        )
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .size(250.dp),
            shape = CircleShape,
            color = WeatherBackground
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(
                    text = "${formatDecimals(weatherItem.temp.day)}º",
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.ExtraBold,
                    color = ColorPrimaryDark
                )
                Text(
                    text = weatherItem.weather[0].main,
                    style = MaterialTheme.typography.h6,
                    fontStyle = FontStyle.Italic,
                    color = ColorAccent
                )
            }
        }
        HumidityWindPressureRow(weather = data.list[0], isImperial = isImperial)
        Divider(
            modifier = Modifier.padding(12.dp), color = ColorPrimary
        )
        SunsetSunRiseRow(weather = data.list[0])
        Text(
            text = "This week",
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = ColorPrimaryDark,
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp), contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) { item ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}

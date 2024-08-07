package com.example.knowledge.compose.jetweatherforecast.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.knowledge.R
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetweatherforecast.data.DataOrException
import com.example.knowledge.compose.jetweatherforecast.model.Weather
import com.example.knowledge.compose.jetweatherforecast.model.WeatherItem
import com.example.knowledge.compose.jetweatherforecast.utils.AppColors
import com.example.knowledge.compose.jetweatherforecast.utils.Constants.BASE_IMAGE_URL
import com.example.knowledge.compose.jetweatherforecast.utils.Constants.IMAGE_EXTENSION
import com.example.knowledge.compose.jetweatherforecast.utils.formatDate
import com.example.knowledge.compose.jetweatherforecast.utils.formatDateTime
import com.example.knowledge.compose.jetweatherforecast.utils.formatDecimals
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
            color = AppColors.ColorSecondaryLight,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(10.dp)
        )
        Surface(
            modifier = Modifier
                .padding(8.dp)
                .size(250.dp),
            shape = CircleShape,
            color = AppColors.WeatherBackground
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
                    color = AppColors.ColorPrimaryDark
                )
                Text(
                    text = weatherItem.weather[0].main,
                    style = MaterialTheme.typography.h6,
                    fontStyle = FontStyle.Italic,
                    color = AppColors.ColorAccent
                )
            }
        }
        HumidityWindPressureRow(weather = data.list[0])
        Divider(
            modifier = Modifier.padding(12.dp),
            color = AppColors.ColorPrimary
        )
        SunsetSunRiseRow(weather = data.list[0])
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

@Composable
fun HumidityWindPressureRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity icon",
                modifier = Modifier.size(20.dp),
                tint = AppColors.ColorSecondary
            )
            Text(
                text = "${weather.humidity}%",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure icon",
                modifier = Modifier.size(20.dp),
                tint = AppColors.ColorSecondary
            )
            Text(
                text = "${weather.humidity} psi",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind icon",
                modifier = Modifier.size(20.dp),
                tint = AppColors.ColorSecondary
            )
            Text(
                text = "${weather.humidity} mph",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}

@Composable
fun SunsetSunRiseRow(weather: WeatherItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, bottom = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "Sunrise",
                modifier = Modifier
                    .size(30.dp),
                colorFilter = ColorFilter.tint(AppColors.ColorSecondary)
            )
            Text(
                text = formatDateTime(weather.sunrise),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 6.dp)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = formatDateTime(weather.sunset),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(end = 6.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "Sunset",
                modifier = Modifier.size(30.dp),
                colorFilter = ColorFilter.tint(AppColors.ColorSecondary)
            )
        }
    }
}

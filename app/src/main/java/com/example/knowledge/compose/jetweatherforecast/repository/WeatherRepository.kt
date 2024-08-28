package com.example.knowledge.compose.jetweatherforecast.repository

import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetweatherforecast.data.DataOrException
import com.example.knowledge.compose.jetweatherforecast.model.Weather
import com.example.knowledge.compose.jetweatherforecast.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(
        cityQuery: String,
        units: String
    ): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        } catch (e: Exception) {
            Logger.log(
                title = "INSIDE excepton",
                message = "getWeather: ${e}"
            )
            return DataOrException(e = e)
        }
        Logger.log(
            title = "INSIDE",
            message = "getWeather: $response"
        )
        return DataOrException(data = response)
    }
}
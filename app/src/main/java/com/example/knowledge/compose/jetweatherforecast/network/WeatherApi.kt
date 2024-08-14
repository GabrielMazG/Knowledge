package com.example.knowledge.compose.jetweatherforecast.network

import com.example.knowledge.compose.jetweatherforecast.model.Weather
import com.example.knowledge.compose.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query(value = "q") query: String,
        @Query(value = "units") units: String = "imperial",
        @Query(value = "appid") appid: String = Constants.API_KEY
    ): Weather
}
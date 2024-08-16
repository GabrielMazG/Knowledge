package com.example.knowledge.compose.jetweatherforecast.repository

import com.example.knowledge.compose.jetweatherforecast.data.WeatherDao
import com.example.knowledge.compose.jetweatherforecast.model.Favorite
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorites()
    suspend fun getFavById(city: String): Favorite = weatherDao.getFavById(city)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun updateFavorite(favorite: Favorite) = weatherDao.updateFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorites() = weatherDao.deleteAllFavorites()
}
package com.example.knowledge.compose.jetweatherforecast.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.knowledge.compose.jetweatherforecast.model.Favorite
import com.example.knowledge.compose.jetweatherforecast.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
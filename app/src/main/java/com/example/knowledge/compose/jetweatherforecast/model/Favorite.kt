package com.example.knowledge.compose.jetweatherforecast.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("fav_tbl")
data class Favorite(
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "city")
    val city: String,
    @ColumnInfo(name = "country")
    val country: String
)

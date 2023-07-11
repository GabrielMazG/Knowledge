package com.example.coroutines.usecases.usecase14

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.coroutines.mock.AndroidVersion

@Entity(tableName = "androidversions")
data class AndroidVersionEntity(@PrimaryKey val apiLevel: Int, val name: String)

fun AndroidVersionEntity.mapToUiModel() = AndroidVersion(this.apiLevel, this.name)

fun List<AndroidVersionEntity>.mapToUiModelList() = map {
    it.mapToUiModel()
}

fun AndroidVersion.mapToEntity() = AndroidVersionEntity(this.apiLevel, this.name)

fun List<AndroidVersion>.mapToEntityList() = map {
    it.mapToEntity()
}
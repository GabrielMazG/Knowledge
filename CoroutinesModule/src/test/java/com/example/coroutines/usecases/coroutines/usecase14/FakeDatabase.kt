package com.example.coroutines.usecases.coroutines.usecase14

import com.example.coroutines.mock.mockAndroidVersions
import com.example.coroutines.usecases.usecase14.AndroidVersionDao
import com.example.coroutines.usecases.usecase14.AndroidVersionEntity
import com.example.coroutines.usecases.usecase14.mapToEntityList

class FakeDatabase : AndroidVersionDao {

    var insertedIntoDb = false

    override suspend fun getAndroidVersions(): List<AndroidVersionEntity> {
        return mockAndroidVersions.mapToEntityList()
    }

    override suspend fun insert(androidVersionEntity: AndroidVersionEntity) {
        insertedIntoDb = true
    }

    override suspend fun clear() {}

}
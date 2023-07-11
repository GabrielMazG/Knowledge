package com.example.coroutines.usecases.coroutines.usecase14

import com.example.coroutines.mock.AndroidVersion
import com.example.coroutines.mock.MockApi
import com.example.coroutines.mock.VersionFeatures
import com.example.coroutines.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay

class FakeApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        delay(1)
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }
}
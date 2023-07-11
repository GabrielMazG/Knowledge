package com.example.coroutines.usecases.coroutines.usecase6

import com.example.coroutines.mock.AndroidVersion
import com.example.coroutines.mock.MockApi
import com.example.coroutines.mock.VersionFeatures
import com.example.coroutines.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay
import java.io.IOException

class FakeSuccessOnThirdAttemptApi(private val responseDelay: Long) : MockApi {

    var requestCount = 0

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        requestCount++
        delay(responseDelay)
        if (requestCount < 3) {
            throw IOException()
        }
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }
}
package com.example.coroutines.usecases.coroutines.usecase3

import com.example.coroutines.mock.*
import com.lukaslechner.coroutineusecasesonandroid.utils.EndpointShouldNotBeCalledException
import kotlinx.coroutines.delay

class FakeSuccessApi(private val responseDelay: Long) : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw EndpointShouldNotBeCalledException()
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        delay(responseDelay)
        return when (apiLevel) {
            27 -> mockVersionFeaturesOreo
            28 -> mockVersionFeaturesPie
            29 -> mockVersionFeaturesAndroid10
            else -> throw IllegalArgumentException("apiLevel not found")
        }
    }
}
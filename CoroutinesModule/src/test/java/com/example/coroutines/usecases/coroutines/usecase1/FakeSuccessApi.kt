package com.example.coroutines.usecases.coroutines.usecase1

import com.example.coroutines.mock.AndroidVersion
import com.example.coroutines.mock.MockApi
import com.example.coroutines.mock.VersionFeatures
import com.example.coroutines.mock.mockAndroidVersions
import com.lukaslechner.coroutineusecasesonandroid.utils.EndpointShouldNotBeCalledException

class FakeSuccessApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }
}
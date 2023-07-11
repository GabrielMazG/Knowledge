package com.example.coroutines.usecases.usecase3

import com.example.coroutines.mock.createMockApi
import com.example.coroutines.mock.mockVersionFeaturesAndroid10
import com.example.coroutines.mock.mockVersionFeaturesOreo
import com.example.coroutines.mock.mockVersionFeaturesPie
import com.google.gson.Gson
import com.example.coroutines.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/android-version-features/27",
            { Gson().toJson(mockVersionFeaturesOreo) },
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/28",
            { Gson().toJson(mockVersionFeaturesPie) },
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/29",
            { Gson().toJson(mockVersionFeaturesAndroid10) },
            200,
            1000
        )
)
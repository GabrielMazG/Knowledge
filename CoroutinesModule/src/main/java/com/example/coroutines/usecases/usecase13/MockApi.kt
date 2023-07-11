package com.example.coroutines.usecases.usecase13

import com.example.coroutines.mock.createMockApi
import com.example.coroutines.mock.mockAndroidVersions
import com.example.coroutines.mock.mockVersionFeaturesAndroid10
import com.example.coroutines.mock.mockVersionFeaturesOreo
import com.example.coroutines.mock.mockVersionFeaturesPie
import com.google.gson.Gson
import com.example.coroutines.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            { Gson().toJson(mockAndroidVersions) },
            200,
            1000
        )
        .mock(
            "http://localhost/android-version-features/27",
            { Gson().toJson(mockVersionFeaturesOreo) },
            MockNetworkInterceptor.INTERNAL_SERVER_ERROR_HTTP_CODE,
            100
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
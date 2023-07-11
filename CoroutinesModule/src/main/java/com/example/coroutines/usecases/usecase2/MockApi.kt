package com.example.coroutines.usecases.usecase2

import com.example.coroutines.mock.createMockApi
import com.example.coroutines.mock.mockAndroidVersions
import com.example.coroutines.mock.mockVersionFeaturesAndroid10
import com.google.gson.Gson
import com.example.coroutines.utils.MockNetworkInterceptor

fun mockApi() = createMockApi(
    MockNetworkInterceptor()
        .mock(
            "http://localhost/recent-android-versions",
            { Gson().toJson(mockAndroidVersions) },
            200,
            1500
        )
        .mock(
            "http://localhost/android-version-features/29",
            { Gson().toJson(mockVersionFeaturesAndroid10) },
            200,
            1500
        )
)
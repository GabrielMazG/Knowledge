package com.example.coroutines.usecases.usecase14

import com.google.gson.Gson
import com.example.coroutines.mock.createMockApi
import com.example.coroutines.mock.mockAndroidVersions
import com.example.coroutines.utils.MockNetworkInterceptor

fun mockApi() =
    createMockApi(
        MockNetworkInterceptor()
            .mock(
                "http://localhost/recent-android-versions",
                { Gson().toJson(mockAndroidVersions) },
                200,
                5000
            )
    )
package com.example.coroutines.usecases.coroutines.usecase2

import com.example.coroutines.mock.AndroidVersion
import com.example.coroutines.mock.MockApi
import com.example.coroutines.mock.VersionFeatures
import com.lukaslechner.coroutineusecasesonandroid.utils.EndpointShouldNotBeCalledException
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeVersionsErrorApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        throw HttpException(
            Response.error<List<AndroidVersion>>(
                500,
                ResponseBody.create(MediaType.parse("application/json"), "")
            )
        )
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        throw EndpointShouldNotBeCalledException()
    }
}
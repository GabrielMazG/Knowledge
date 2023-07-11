package com.example.coroutines.usecases.coroutines.usecase2

import com.example.coroutines.mock.AndroidVersion
import com.example.coroutines.mock.MockApi
import com.example.coroutines.mock.VersionFeatures
import com.example.coroutines.mock.mockAndroidVersions
import com.example.coroutines.mock.mockVersionFeaturesOreo
import com.example.coroutines.mock.mockVersionFeaturesPie
import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response

class FakeFeaturesErrorApi : MockApi {

    override suspend fun getRecentAndroidVersions(): List<AndroidVersion> {
        return mockAndroidVersions
    }

    override suspend fun getAndroidVersionFeatures(apiLevel: Int): VersionFeatures {
        return when (apiLevel) {
            27 -> mockVersionFeaturesOreo
            28 -> mockVersionFeaturesPie
            29 -> throw HttpException(
                Response.error<List<AndroidVersion>>(
                    500,
                    ResponseBody.create(MediaType.parse("application/json"), "")
                )
            )

            else -> throw IllegalArgumentException("apiLevel not found")
        }
    }
}
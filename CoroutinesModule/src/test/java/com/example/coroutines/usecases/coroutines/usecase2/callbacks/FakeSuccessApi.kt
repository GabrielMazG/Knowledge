package com.example.coroutines.usecases.coroutines.usecase2.callbacks

import com.example.coroutines.mock.*
import com.example.coroutines.usecases.usecase2.callbacks.CallbackMockApi
import retrofit2.Call
import retrofit2.mock.Calls

class FakeSuccessApi : CallbackMockApi {

    override fun getRecentAndroidVersions(): Call<List<AndroidVersion>> {
        return Calls.response(mockAndroidVersions)
    }

    override fun getAndroidVersionFeatures(apiLevel: Int): Call<VersionFeatures> {
        val featureMocks = when (apiLevel) {
            27 -> mockVersionFeaturesOreo
            28 -> mockVersionFeaturesPie
            29 -> mockVersionFeaturesAndroid10
            else -> throw IllegalArgumentException("apiLevel not found")
        }
        return Calls.response(featureMocks)
    }
}
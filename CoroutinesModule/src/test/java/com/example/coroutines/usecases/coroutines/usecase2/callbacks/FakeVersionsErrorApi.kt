package com.example.coroutines.usecases.coroutines.usecase2.callbacks

import com.example.coroutines.mock.AndroidVersion
import com.example.coroutines.mock.VersionFeatures
import com.example.coroutines.mock.mockAndroidVersions
import com.example.coroutines.usecases.usecase2.callbacks.CallbackMockApi
import retrofit2.Call
import retrofit2.mock.Calls
import java.io.IOException

class FakeVersionsErrorApi : CallbackMockApi {

    override fun getRecentAndroidVersions(): Call<List<AndroidVersion>> {
        return Calls.response(mockAndroidVersions)
    }

    override fun getAndroidVersionFeatures(apiLevel: Int): Call<VersionFeatures> {
        return Calls.failure(IOException())
    }
}
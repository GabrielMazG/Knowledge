package com.example.coroutines.usecases.usecase15

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.mock.createMockAnalyticsApi
import com.example.coroutines.utils.MockNetworkInterceptor

class AnalyticsWorker(appContext: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(appContext, workerParameters) {

    private val analyticsApi = createMockAnalyticsApi()

    override suspend fun doWork(): Result {
        return try {
            analyticsApi.trackScreenOpened()
            Log.d(TAG, "doWork: Successfully tracked screen open event!")
            Result.success()
        } catch (exception: Exception) {
            Log.d(TAG, "Tracking screen open event failed!")
            Result.failure()
        }
    }

    companion object {
        fun createMockAnalyticsApi() = createMockAnalyticsApi(
            MockNetworkInterceptor()
                .mock(
                    "http://localhost/analytics/workmanager-screen-opened",
                    { "true" },
                    200,
                    1500
                )
        )
    }
}
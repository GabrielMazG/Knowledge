package com.example.coroutines.usecases.usecase9

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.mock.MockApi
import com.lukaslechner.coroutineusecasesonandroid.utils.addCoroutineDebugInfo
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DebuggingCoroutinesViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading

        // This property needs to be set so that the coroutine name is printed when logging Thread.currentName()
        // System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
        // It is set in [CoroutineUsecasesOnAndroidApplication]

        viewModelScope.launch(CoroutineName("Initial Coroutine")) {
            Log.d(
                TAG,
                "performSingleNetworkRequest: ${addCoroutineDebugInfo("Initial coroutine launched")}"
            )
            try {
                val recentVersions = api.getRecentAndroidVersions()
                Log.d(
                    TAG,
                    "performSingleNetworkRequest: ${addCoroutineDebugInfo("Recent Android Versions returned")}"
                )
                uiState.value = UiState.Success(recentVersions)
            } catch (exception: Exception) {
                Log.d(
                    TAG,
                    "performSingleNetworkRequest: ${addCoroutineDebugInfo("Loading recent Android Versions failed")}"
                )
                uiState.value = UiState.Error("Network Request failed")
            }

            // Perform two calculations in parallel
            val calculation1Deferred =
                async(CoroutineName("Calculation1")) { performCalculation1() }
            val calculation2Deferred =
                async(CoroutineName("Calculation2")) { performCalculation2() }

            Log.d(
                TAG,
                "performSingleNetworkRequest: ${addCoroutineDebugInfo("Result of Calculation1: ${calculation1Deferred.await()}")}"
            )
            Log.d(
                TAG,
                "performSingleNetworkRequest: ${addCoroutineDebugInfo("Result of Calculation2: ${calculation2Deferred.await()}")}"
            )
        }
    }

    private suspend fun performCalculation1() = withContext(Dispatchers.Default) {
        Log.d(TAG, "performSingleNetworkRequest: ${addCoroutineDebugInfo("Starting Calculation1")}")
        delay(1000)
        Log.d(
            TAG,
            "performSingleNetworkRequest: ${addCoroutineDebugInfo("Calculation1 completed")}"
        )
        13
    }

    private suspend fun performCalculation2() = withContext(Dispatchers.Default) {
        Log.d(TAG, "performSingleNetworkRequest: ${addCoroutineDebugInfo("Starting Calculation2")}")
        delay(2000)
        Log.d(
            TAG,
            "performSingleNetworkRequest: ${addCoroutineDebugInfo("Calculation2 completed")}"
        )
        42
    }
}
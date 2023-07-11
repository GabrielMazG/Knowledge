package com.example.coroutines.usecases.usecase7

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.mock.MockApi
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class TimeoutAndRetryViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        val numberOfRetries = 2
        val timeout = 1000L

        val oreoVersionsDeferred = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(27)
            }
        }

        val pieVersionsDeferred = viewModelScope.async {
            retryWithTimeout(numberOfRetries, timeout) {
                api.getAndroidVersionFeatures(28)
            }
        }

        viewModelScope.launch {
            try {
                val versionFeatures = listOf(
                    oreoVersionsDeferred,
                    pieVersionsDeferred
                ).awaitAll()

                uiState.value = UiState.Success(versionFeatures)

            } catch (e: Exception) {
                Log.d(TAG, "performNetworkRequest: ${e.message}")
                uiState.value = UiState.Error("Network Request failed")
            }
        }
    }

    private suspend fun <T> retryWithTimeout(
        numberOfRetries: Int,
        timeout: Long,
        block: suspend () -> T
    ) = retry(numberOfRetries) {
        withTimeout(timeout) {
            block()
        }
    }

    private suspend fun <T> retry(
        numberOfRetries: Int,
        delayBetweenRetries: Long = 100,
        block: suspend () -> T
    ): T {
        repeat(numberOfRetries) {
            try {
                return block()
            } catch (exception: Exception) {
                Log.d(TAG, "performNetworkRequest: ${exception.message}")
            }
            delay(delayBetweenRetries)
        }
        return block() // last attempt
    }
}
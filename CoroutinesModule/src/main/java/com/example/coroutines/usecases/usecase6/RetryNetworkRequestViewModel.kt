package com.example.coroutines.usecases.usecase6

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.mock.MockApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RetryNetworkRequestViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            val numberOfRetries = 2
            try {
                retry(times = numberOfRetries) {
                    val recentVersions = api.getRecentAndroidVersions()
                    uiState.value = UiState.Success(recentVersions)
                }
            } catch (e: Exception) {
                uiState.value = UiState.Error("Network Request failed")
            }
        }
    }

    // retry with exponential backoff
    // inspired by https://stackoverflow.com/questions/46872242/how-to-exponential-backoff-retry-on-kotlin-coroutines
    private suspend fun <T> retry(
        times: Int,
        initialDelayMillis: Long = 100,
        maxDelayMillis: Long = 1000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelayMillis
        repeat(times) {
            try {
                return block()
            } catch (exception: Exception) {
                Log.d(TAG, "retry: ${exception.message}")
            }
            delay(currentDelay)
            currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
        }
        return block() // last attempt
    }
}
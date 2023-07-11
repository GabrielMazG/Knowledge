package com.example.coroutines.usecases.usecase13

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.mock.MockApi
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ExceptionHandlingViewModel(
    private val api: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun handleExceptionWithTryCatch() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                api.getAndroidVersionFeatures(27)

            } catch (exception: Exception) {
                uiState.value = UiState.Error("Network Request failed: $exception")
            }
        }
    }

    fun handleWithCoroutineExceptionHandler() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            uiState.value = UiState.Error("Network Request failed!! $exception")
        }

        uiState.value = UiState.Loading
        viewModelScope.launch(exceptionHandler) {
            api.getAndroidVersionFeatures(27)
        }
    }

    fun showResultsEvenIfChildCoroutineFails() {
        uiState.value = UiState.Loading
        viewModelScope.launch {

            supervisorScope {
                val oreoFeaturesDeferred = async { api.getAndroidVersionFeatures(27) }
                val pieFeaturesDeferred = async { api.getAndroidVersionFeatures(28) }
                val android10FeaturesDeferred = async { api.getAndroidVersionFeatures(29) }

                val versionFeatures = listOf(
                    oreoFeaturesDeferred,
                    pieFeaturesDeferred,
                    android10FeaturesDeferred
                ).mapNotNull {
                    try {
                        it.await()
                    } catch (exception: Exception) {
                        // We have to re-throw cancellation exceptions so that
                        // our Coroutine gets cancelled immediately.
                        // Otherwise, the CancellationException is ignored
                        // and the Coroutine keeps running until it reaches the next
                        // suspension point.
                        if (exception is CancellationException) {
                            throw exception
                        }
                        Log.d(
                            TAG,
                            "showResultsEvenIfChildCoroutineFails: Error loading feature data!"
                        )
                        null
                    }
                }
                uiState.value = UiState.Success(versionFeatures)
            }
        }
    }
}
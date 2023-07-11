package com.example.coroutines.usecases.usecase1

import androidx.lifecycle.viewModelScope
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.mock.MockApi
import kotlinx.coroutines.launch

class PerformSingleNetworkRequestViewModel(
    private val mockApi: MockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun performSingleNetworkRequest() {
        uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                val recentAndroidVersions = mockApi.getRecentAndroidVersions()
                uiState.value = UiState.Success(recentAndroidVersions)
            } catch (exception: Exception) {
                println("$TAG, performSingleNetworkRequest: ${exception.message}")
                uiState.value = UiState.Error("Network Request failed!")
            }
        }
    }
}
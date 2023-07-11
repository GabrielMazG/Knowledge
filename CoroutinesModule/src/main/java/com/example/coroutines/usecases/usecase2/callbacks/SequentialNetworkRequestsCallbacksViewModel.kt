package com.example.coroutines.usecases.usecase2.callbacks

import com.example.coroutines.base.BaseViewModel

class SequentialNetworkRequestsCallbacksViewModel(
    private val mockApi: CallbackMockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {

    }
}
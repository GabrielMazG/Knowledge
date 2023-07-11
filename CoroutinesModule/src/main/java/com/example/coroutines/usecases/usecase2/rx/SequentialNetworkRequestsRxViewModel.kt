package com.example.coroutines.usecases.usecase2.rx

import com.example.coroutines.base.BaseViewModel

class SequentialNetworkRequestsRxViewModel(
    private val mockApi: RxMockApi = mockApi()
) : BaseViewModel<UiState>() {

    fun perform2SequentialNetworkRequest() {

    }
}
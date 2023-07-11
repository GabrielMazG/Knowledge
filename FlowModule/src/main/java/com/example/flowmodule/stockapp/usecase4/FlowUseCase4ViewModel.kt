package com.example.flowmodule.stockapp.usecase4

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.flowmodule.FlowActivity.Companion.TAG
import com.example.flowmodule.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn

class FlowUseCase4ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPriceAsShareFlow: Flow<UiState> = stockPriceDataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .onCompletion {
            Log.d(TAG, ": Flow has completed.")
        }
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
            replay = 1
        )

    // Hot state flow
    val currentStockPriceAsStateFlow: Flow<UiState> = stockPriceDataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onCompletion {
            Log.d(TAG, ": Flow has completed.")
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = UiState.Loading,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
        )

    val currentStockPriceAsFlow: StateFlow<UiState> = stockPriceDataSource
        .latestStockList
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onCompletion {
            Log.d(TAG, "Flow has completed.")
        }.stateIn(
            scope = viewModelScope,
            initialValue = UiState.Loading,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000)
        )
}
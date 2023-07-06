package com.example.flowmodule.stockapp.usecase1

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.flowmodule.base.BaseViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class FlowUseCase1ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    val currentStockPriceAsLiveData: LiveData<UiState> = stockPriceDataSource
        .latestStockList
        .map { stockList -> // Apply to each element from the Flow<T> and return a Flow<R>
            UiState.Success(stockList) as UiState
        }
        .onStart { // Runs when flow init
            emit(UiState.Loading)
        }
        .onCompletion { // Runs when flow ends or is canceled
            Log.d("Flow", "Flow has completed.")
        }
        .asLiveData() // Here we avoid to cancel flow when change device state (orientation, dark mode, etc) and return a LiveData
}
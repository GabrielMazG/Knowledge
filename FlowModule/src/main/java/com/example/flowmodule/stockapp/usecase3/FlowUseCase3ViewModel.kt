package com.example.flowmodule.stockapp.usecase3

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.flowmodule.FlowActivity.Companion.TAG
import com.example.flowmodule.base.BaseViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

class FlowUseCase3ViewModel(
    stockPriceDataSource: StockPriceDataSource
) : BaseViewModel<UiState>() {

    /*
        Exercise: Flow Exception Handling

        Tasks:
        - Adjust code in StockPriceDataSource and FlowUseCase3ViewModel

        Exception Handling Goals:
        - for HttpExceptions in the datasource
            - re-collect from the flow
            - delay for 5 seconds before re-collecting the flow
        - for all other Exceptions within the whole flow pipeline
            - show toast with error message by emitting UiState.Error
     */

    val currentStockPriceAsLiveData: LiveData<UiState> = stockPriceDataSource
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
        .catch { error ->
            Log.d(TAG, "Error: ${error.message.toString()}")
            emit(UiState.Error("Error: ${error.message.toString()}"))
        }
        .asLiveData()
}
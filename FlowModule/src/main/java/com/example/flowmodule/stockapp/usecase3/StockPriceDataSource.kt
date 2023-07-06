package com.example.flowmodule.stockapp.usecase3

import android.util.Log
import com.example.flowmodule.FlowActivity.Companion.TAG
import com.example.flowmodule.stockapp.mock.FlowMockApi
import com.example.flowmodule.stockapp.mock.Stock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retry
import retrofit2.HttpException

interface StockPriceDataSource {
    val latestStockList: Flow<List<Stock>>
}

class NetworkStockPriceDataSource(mockApi: FlowMockApi) : StockPriceDataSource {

    override val latestStockList: Flow<List<Stock>> = flow<List<Stock>> {
        while (true) {
            Log.d(TAG, ": Fetching current stock prices")
            val currentStockList = mockApi.getCurrentStockPrices()
            emit(currentStockList)
            delay(5_000)
        }
    }.retry { error ->
        Log.d(TAG, ": Enter retry ${error.cause}")

        val shouldRetry = error is HttpException
        if (shouldRetry) delay(5000)

        shouldRetry
    }
}
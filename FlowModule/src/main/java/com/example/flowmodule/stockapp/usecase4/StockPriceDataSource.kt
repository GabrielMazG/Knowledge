package com.example.flowmodule.stockapp.usecase4

import android.util.Log
import com.example.flowmodule.FlowActivity.Companion.TAG
import com.example.flowmodule.stockapp.mock.FlowMockApi
import com.example.flowmodule.stockapp.mock.Stock
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface StockPriceDataSource {
    val latestStockList: Flow<List<Stock>>
}

class NetworkStockPriceDataSource(mockApi: FlowMockApi) : StockPriceDataSource {

    override val latestStockList: Flow<List<Stock>> = flow {
        while (true) {
            Log.d(TAG, "Fetching current stock prices")
            val currentStockList = mockApi.getCurrentStockPrices()
            emit(currentStockList)
            delay(5_000)
        }
    }
}
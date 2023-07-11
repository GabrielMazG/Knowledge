package com.example.flowmodule.flow.usecase4

import com.example.flowmodule.stockapp.mock.Stock
import com.example.flowmodule.stockapp.usecase4.StockPriceDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class FakeStockPriceDataSource : StockPriceDataSource {

    private val sharedFlow = MutableSharedFlow<List<Stock>>()

    suspend fun emit(stockList: List<Stock>) {
        sharedFlow.emit(stockList)
    }

    override val latestStockList: Flow<List<Stock>> = sharedFlow.asSharedFlow()

}

package com.example.flowmodule.stockapp.usecase1

import android.content.Context
import com.example.flowmodule.stockapp.mock.MockNetworkInterceptor
import com.example.flowmodule.stockapp.mock.createFlowMockApi
import com.example.flowmodule.stockapp.mock.fakeCurrentStockPrices
import com.google.gson.Gson

fun mockApi(context: Context) =
    createFlowMockApi(
        MockNetworkInterceptor()
            .mock(
                path = "/current-stock-prices",
                body = { Gson().toJson(fakeCurrentStockPrices(context)) },
                status = 200,
                delayInMs = 1500,
            )
    )
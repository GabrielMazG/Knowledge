package com.example.flowmodule.stockapp.usecase1

import com.example.flowmodule.stockapp.mock.Stock

sealed class UiState {
    object Loading : UiState()
    data class Success(val stockList: List<Stock>) : UiState()
    data class Error(val message: String) : UiState()
}
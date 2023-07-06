package com.example.flowmodule.stockapp.usecase2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.flowmodule.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.withIndex

class FlowUseCase2ViewModel(
    stockPriceDataSource: StockPriceDataSource,
    defaultDispatcher: CoroutineDispatcher
) : BaseViewModel<UiState>() {

    /*

    Flow exercise 1 Goals
        1) only update stock list when Alphabet(Google) (stock.name ="Alphabet (Google)") stock price is > 2300$
        2) only show stocks of "United States" (stock.country == "United States")
        3) show the correct rank (stock.rank) within "United States", not the world wide rank
        4) filter out Apple  (stock.name ="Apple") and Microsoft (stock.name ="Microsoft"), so that Google is number one
        5) only show company if it is one of the biggest 10 companies of the "United States" (stock.rank <= 10)
        6) stop flow collection after 10 emissions from the dataSource
        7) log out the number of the current emission so that we can check if flow collection stops after exactly 10 emissions
        8) Perform all flow processing on a background thread

     */

    val currentStockPriceAsLiveData: LiveData<UiState> = stockPriceDataSource
        .latestStockList
        .withIndex()  // 7) Return the index and the value of the emission
        .onEach { indexedValue ->  // 7)
            Log.d("FlowUseCase2ViewModel", ": Processing emission ${indexedValue.index + 1}")
        }
        .map { indexedValue ->  // Return again only the value
            indexedValue.value
        }
        .take(10) // 6)
        .filter { stockList -> // 1)
            val price = stockList.find { stock ->
                stock.name == "Alphabet (Google)"
            }?.currentPrice ?: 0f

            price > 2300
        }
        .map { stockList -> // 2)
            stockList.filter { stock ->
                stock.country == "United States"
            }
        }
        .cancellable()
        .map { stockList -> // 4)
            stockList.filter { stock ->
                stock.name != "Apple" && stock.name != "Microsoft"
            }
        }
        .map { stockList -> // 3)
            stockList.mapIndexed { index, stock ->
                stock.copy(rank = index + 1)
            }
        }
        .map { stockList -> // 5)
            stockList.filter { stock ->
                stock.rank <= 10
            }
        }
        .map { stockList ->
            UiState.Success(stockList) as UiState
        }
        .onStart {
            emit(UiState.Loading)
        }
        .asLiveData(defaultDispatcher)  // 8)
}
package com.example.flowmodule.stockapp.usecase1

import android.os.Bundle
import androidx.activity.viewModels
import com.example.flowmodule.base.BaseActivity
import com.example.flowmodule.base.flowUseCase1Description
import com.example.flowmodule.databinding.ActivityFlowUsecase1Binding
import com.example.flowmodule.extensions.setGone
import com.example.flowmodule.extensions.setVisible
import com.example.flowmodule.extensions.toast
import org.joda.time.LocalDateTime
import org.joda.time.format.DateTimeFormat

class FlowUseCase1Activity : BaseActivity() {

    private lateinit var binding: ActivityFlowUsecase1Binding
    private val adapter = StockAdapter()

    private val viewModel: FlowUseCase1ViewModel by viewModels {
        ViewModelFactory(NetworkStockPriceDataSource(mockApi(applicationContext)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowUsecase1Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.recyclerView.adapter = adapter

        viewModel.currentStockPriceAsLiveData.observe(this) { uiState ->
            uiState?.let {
                render(it)
            }
        }
    }

    private fun render(uiState: UiState) {
        when (uiState) {
            is UiState.Loading -> {
                binding.progressBar.setVisible()
                binding.recyclerView.setGone()
            }

            is UiState.Success -> {
                binding.recyclerView.setVisible()
                binding.lastUpdateTime.text =
                    "LastUpdateTime: ${LocalDateTime.now().toString(DateTimeFormat.fullTime())}"
                adapter.stockList = uiState.stockList
                binding.progressBar.setGone()
            }

            is UiState.Error -> {
                toast(uiState.message)
                binding.progressBar.setGone()
            }
        }
    }

    override fun getToolbarTitle() = flowUseCase1Description
}
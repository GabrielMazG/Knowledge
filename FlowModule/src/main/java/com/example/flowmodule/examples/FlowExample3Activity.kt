package com.example.flowmodule.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flowmodule.databinding.ActivityFlowExampleBinding
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class FlowExample3Activity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowExampleBinding

    // In viewModel
    private val _uiState = MutableStateFlow<UiState>(UiState.Success(emptyList<String>()))

    // Immutable state flow, UI will updates accordingly StateFlow
    val uiState: StateFlow<UiState> = _uiState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowExampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    init {
        lifecycleScope.launch {
            _uiState.tryEmit(UiState.Loading)
            // showRepository.fetchUpcomingShow() or some api call
            val flow = flowOf(listOf("Element 1", "Element 2", "Element 3"))
            flow.collect { shows ->
                _uiState.tryEmit(UiState.Success(shows))
                Log.d(TAG, "$shows emitted to UI")
            }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Success(val shows: List<String>) : UiState()
    }

    companion object {
        var TAG: String = this::class.java.name
    }
}
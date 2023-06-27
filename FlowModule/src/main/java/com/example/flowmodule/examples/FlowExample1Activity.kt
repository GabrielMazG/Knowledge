package com.example.flowmodule.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flowmodule.databinding.ActivityFlowExampleBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowExample1Activity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowExampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    init {
        lifecycleScope.launch { main() }
    }

    private suspend fun main() {
        val flow = flow {
            for (n in 0..9) {
                delay(2000)
                emit(n)
            }
        }
        flow.collect {
            delay(3000)
            Log.d(TAG, "Consuming: $it")
        }
    }

    companion object {
        var TAG: String = this::class.java.name
    }
}
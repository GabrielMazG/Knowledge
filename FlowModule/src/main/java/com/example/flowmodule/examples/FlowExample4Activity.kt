package com.example.flowmodule.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flowmodule.databinding.ActivityFlowExampleBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.runBlocking

class FlowExample4Activity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowExampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    init {
        runBlocking {
            val userFlow = MutableSharedFlow<String>(replay = 2)

            userFlow.tryEmit("User 0")
            userFlow.tryEmit("User 1")
            userFlow.tryEmit("User 2")

            val job1 = userFlow.onEach {
                Log.d(TAG, "1 - Collected $it")
            }.launchIn(this)

            val job2 = userFlow.onEach {
                Log.d(TAG, "2 - Collected $it")
            }.launchIn(this)

            delay(1000)
            job1.cancel()
            delay(2000)
            job2.cancel()
        }
    }

    companion object {
        var TAG: String = this::class.java.name
    }
}
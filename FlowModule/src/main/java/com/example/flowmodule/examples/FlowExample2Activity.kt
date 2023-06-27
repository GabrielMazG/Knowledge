package com.example.flowmodule.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.flowmodule.databinding.ActivityFlowExampleBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

class FlowExample2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowExampleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowExampleBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    init {
        main()
    }

    private fun getSequence(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            Log.d(TAG, "Emit $i")
            emit(i)
        }
    }

    private fun main() = runBlocking {
        val f = getSequence()
        Log.d(TAG, "Start to collect")
        f.collect { value ->
            Log.d(TAG, "Collecting: $value")
        }
    }

    companion object {
        var TAG: String = this::class.java.name
    }
}
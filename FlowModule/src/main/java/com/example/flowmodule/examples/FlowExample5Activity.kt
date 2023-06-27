package com.example.flowmodule.examples

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.commonmodule.extensions.addText
import com.example.commonmodule.extensions.clearText
import com.example.flowmodule.databinding.ActivityHotColdFlowBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FlowExample5Activity : AppCompatActivity() {

    private lateinit var binding: ActivityHotColdFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotColdFlowBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            hotFlow.setOnClickListener {
                hotFlow()
            }

            coldFlow.setOnClickListener {
                coldFlow()
            }

            hotFlowWithoutCancellationException.setOnClickListener {
                hotFlowWithoutCancellationException()
            }

            coldFlowWithoutCancellationException.setOnClickListener {
                coldFlowWithoutCancellationException()
            }
        }
    }

    private fun hotFlow() = lifecycleScope.launch(Dispatchers.IO) {
        clearText()
        val hotFlow = MutableSharedFlow<Int>()

        // Emisión continua de elementos en el Hot Flow
        launch {
            var counter = 0
            while (true) {
                hotFlow.emit(counter)
                counter++
                delay(500) // Espera 1 segundo antes de la próxima emisión
            }
        }

        // Consumidor 1
        launch {
            hotFlow.collect { value ->
                addText("Consumidor 1: $value")
                delay(1000) // Simula un procesamiento más lento
            }
        }

        // Consumidor 2
        launch {
            delay(2000) // Espera 2 segundos antes de la suscripción
            hotFlow.collect { value ->
                addText("Consumidor 2: $value")
            }
        }

        delay(5000) // Espera 5 segundos para mostrar la salida completa
        try {
            cancel() // Cancela todos los coroutines en ejecución

        } catch (ex: Exception) {
            addText("${ex.message}")
        }
    }

    private fun coldFlow() = lifecycleScope.launch(Dispatchers.IO) {
        clearText()
        val coldFlow = flow {
            repeat(5) { index ->
                delay(1000) // Espera 1 segundo entre cada emisión
                emit(index)
            }
        }

        // Consumidor 1
        launch {
            coldFlow.collect { value ->
                addText("Consumidor 1: $value")
                delay(500) // Simula un procesamiento más lento
            }
        }

        // Consumidor 2
        launch {
            delay(3000) // Espera 3 segundos antes de la suscripción
            coldFlow.collect { value ->
                addText("Consumidor 2: $value")
            }
        }

        delay(6000) // Espera 6 segundos para mostrar la salida completa
        cancel() // Cancela todos los coroutines en ejecución
        addText("Finish")
    }

    private fun hotFlowWithoutCancellationException() = lifecycleScope.launch(Dispatchers.IO) {
        clearText()
        val hotFlow = MutableSharedFlow<Int>(3)

        // Emisión continua de elementos en el Hot Flow
        val job0 = launch {
            var counter = 0
            while (true) {
                hotFlow.emit(counter)
                counter++
                delay(500) // Espera 1 segundo antes de la próxima emisión
            }
        }

        // Consumidor 1
        val job1 = hotFlow.onEach { value ->
            addText("Consumidor 1: $value")
            delay(1000) // Simula un procesamiento más lento
        }.launchIn(this)

        // Consumidor 2
        delay(2500) // Espera 2 segundos antes de la suscripción
        val job2 = hotFlow.onEach { value ->
            addText("Consumidor 2: $value")
        }.launchIn(this)


        delay(9000) // Espera 5 segundos para mostrar la salida completa
        job0.cancel() // Cancela todos los coroutines en ejecución
        job1.cancel() // Cancela todos los coroutines en ejecución
        job2.cancel() // Cancela todos los coroutines en ejecución
        addText("Finish")
    }

    private fun coldFlowWithoutCancellationException() = lifecycleScope.launch(Dispatchers.IO) {
        clearText()
        val coldFlow = flow {
            repeat(5) { index ->
                delay(1000) // Espera 1 segundo entre cada emisión
                emit(index)
            }
        }

        // Consumidor 1
        val job1 = coldFlow.onEach { value ->
            addText("Consumidor 1: $value")
            Log.d(TAG, "Consumidor 1: $value")
            delay(500) // Simula un procesamiento más lento
        }.launchIn(this)

        // Consumidor 2

        delay(3000) // Espera 3 segundos antes de la suscripción
        val job2 = coldFlow.onEach { value ->
            addText("Consumidor 2: $value")
            Log.d(TAG, "Consumidor 2: $value")
        }.launchIn(this)

        delay(6000) // Espera 6 segundos para mostrar la salida completa
        job1.cancel() // Cancela todos los coroutines en ejecución
        job2.cancel() // Cancela todos los coroutines en ejecución
        addText("Finish")
    }

    private suspend fun clearText() {
        binding.resultContainer.clearText()
    }

    private suspend fun addText(output: String) {
        binding.resultContainer.addText(output)
    }

    companion object {
        var TAG: String = this::class.java.name
    }
}
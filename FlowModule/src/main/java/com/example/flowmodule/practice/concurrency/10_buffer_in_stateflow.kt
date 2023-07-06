package com.example.flowmodule.practice.concurrency

import com.example.flowmodule.extensions.printlnTime
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

suspend fun main(): Unit = coroutineScope {
    val startTime = System.currentTimeMillis()
    val flow = MutableStateFlow(0)

    // Collector 1
    launch {
        flow.collect {
            printlnTime(startTime, "Collector 1 processes $it")
        }
    }

    // Collector 2
    launch {
        flow.collect {
            printlnTime(startTime, "Collector 2 processes $it")
            delay(100)
        }
    }

    // Emitter
    launch {
        val timeToEmit = measureTimeMillis {
            repeat(5) {
                flow.emit(it)
                delay(10)
            }
        }
        printlnTime(startTime, "Time to emit all values: $timeToEmit ms")
    }
}
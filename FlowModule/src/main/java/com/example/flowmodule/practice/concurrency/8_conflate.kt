package com.example.flowmodule.practice.concurrency

import com.example.flowmodule.extensions.printlnTime
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow

suspend fun main() = coroutineScope {
    val startTime = System.currentTimeMillis()
    printlnTime(startTime)

    val flow = flow {
        repeat(5) {
            printlnTime(startTime, "Emitter:    Start Cooking Pancake $it")
            delay(100)
            printlnTime(startTime, "Emitter:    Pancake $it ready!")
            emit(it)
        }
    }.conflate()

    flow.collect {
        printlnTime(startTime, "Collector:  Start eating pancake $it")
        delay(300)
        printlnTime(startTime, "Collector:  Finished eating pancake $it")
    }
}
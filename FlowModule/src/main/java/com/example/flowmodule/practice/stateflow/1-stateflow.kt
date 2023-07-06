package com.example.flowmodule.practice.stateflow

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

suspend fun main() {

    val counter = MutableStateFlow(0)

    println(counter.value)

    coroutineScope {
        repeat(100) {
            launch {
                counter.update { currentValue ->
                    println(counter.value)
                    currentValue + 1
                }
            }
        }
    }
    println(counter.value)
}
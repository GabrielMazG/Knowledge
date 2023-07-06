package com.example.flowmodule.practice.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.transform

suspend fun main() {

    flowOf(1, 2, 3, 4, 5, 1)
        .transform {
            emit(it)
            emit(it * 10)
            emit(it * 100)
        }
        .collect { collectedValue ->
            println(collectedValue)
        }
}
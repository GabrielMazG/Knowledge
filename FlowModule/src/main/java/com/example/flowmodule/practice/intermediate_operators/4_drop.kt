package com.example.flowmodule.practice.intermediate_operators

import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.flowOf

suspend fun main() {

    flowOf(1, 2, 3, 4, 5, 1, 2, 3, 4, 5)
        .dropWhile { it == 3 }
        .collect { collectedValue ->
            println(collectedValue)
        }
}
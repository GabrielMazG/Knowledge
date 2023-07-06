package com.example.flowmodule.practice.intermediate_operators

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.withIndex

suspend fun main() {

    flowOf(1, 2, 3, 4, 5, 1)
        .withIndex()
        .collect { collectedValue ->
            println(collectedValue)
            println("Index: ${collectedValue.index} - Value: ${collectedValue.value}")
        }
}
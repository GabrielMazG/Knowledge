package com.example.flowmodule.practice.terminal_operators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.runBlocking

fun main() {

    val flow = flow {
        delay(100)

        println("Emitting first value")
        emit(1)

        delay(100)

        println("Emitting second value")
        emit(2)
    }

    runBlocking {
        val item = flow.fold(5) { accumulator, emittedItem ->
            println("$accumulator - $emittedItem")
            accumulator + emittedItem
        }
        println("Received $item")
    }
}
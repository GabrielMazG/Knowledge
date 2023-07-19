package com.example.flowmodule.practice.channels

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay

suspend fun main(): Unit = coroutineScope {

    val deferredUser = async { fetchUserData("123") }
    val deferredDetails = async { fetchUserDetails("123") }

    // Realiza otras tareas mientras se espera el resultado

    println("----")
    val user = deferredUser.await()
    // Realiza acciones con el resultado de fetchUserData
    println(user.name)
    // Realiza otras tareas mientras se espera el resultado de fetchUserDetails

    val details = deferredDetails.await()
    println(details.name)
    println("----")
}

suspend fun fetchUserData(userId: String): User {
    // Simula una operación asíncrona
    delay(1000)
    return User("John", "Doe")
}

suspend fun fetchUserDetails(userId: String): UserDetails {
    // Simula una operación asíncrona
    delay(1500)
    return UserDetails("john.doe@example.com", "1234567890")
}

class User(val name: String, val age: String)
class UserDetails(val name: String, val age: String)
package com.example.knowledge.compose.jettriviaapp.data

data class DataOrException<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E?  = null

)
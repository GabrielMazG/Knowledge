package com.example.knowledge.compose.jetreaderapp.model

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)
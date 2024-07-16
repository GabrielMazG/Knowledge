package com.example.knowledge.compose.jettriviaapp.screens

import androidx.compose.runtime.Composable

@Composable
fun TriviaHome(viewModel: QuestionsViewModel) {
    Questions(viewModel)
}
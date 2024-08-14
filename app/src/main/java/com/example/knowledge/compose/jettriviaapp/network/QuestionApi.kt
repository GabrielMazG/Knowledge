package com.example.knowledge.compose.jettriviaapp.network

import com.example.knowledge.compose.jettriviaapp.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions(): Question
}
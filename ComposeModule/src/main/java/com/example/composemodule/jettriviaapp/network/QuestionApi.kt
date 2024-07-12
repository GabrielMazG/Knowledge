package com.example.composemodule.jettriviaapp.network

import com.example.composemodule.jettriviaapp.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionApi {

    @GET("world.json")
    suspend fun getAllQuestions(): Question
}
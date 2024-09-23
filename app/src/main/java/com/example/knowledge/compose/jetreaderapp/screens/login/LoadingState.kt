package com.example.knowledge.compose.jetreaderapp.screens.login

data class LoadingState(val status: Status, val message: String? = null) {

    companion object {
        val SUCCESS = LoadingState(Status.SUCCESS)
        val FAILURE = LoadingState(Status.FAILURE)
        val LOADING = LoadingState(Status.LOADING)
        val IDLE = LoadingState(Status.IDLE)
    }

    enum class Status {
        SUCCESS,
        FAILURE,
        LOADING,
        IDLE
    }
}
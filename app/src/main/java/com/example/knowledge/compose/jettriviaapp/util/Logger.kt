package com.example.knowledge.compose.jettriviaapp.util

import android.util.Log
import com.example.knowledge.compose.jettriviaapp.util.Constants.LOG_TAG

object Logger {
    fun log(title: String, message: String) {
        Log.d(LOG_TAG, "$title - $message ")
    }
}
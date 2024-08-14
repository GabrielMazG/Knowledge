package com.example.knowledge.compose

import android.util.Log
import com.example.knowledge.compose.Constants.LOG_TAG

object Logger {
    fun log(
        tag: String = LOG_TAG,
        title: String,
        message: String
    ) {
        Log.d(tag, "$title - $message ")
    }
}
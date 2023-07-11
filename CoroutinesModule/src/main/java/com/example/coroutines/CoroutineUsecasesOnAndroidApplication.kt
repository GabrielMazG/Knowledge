package com.example.coroutines

import android.app.Application
import android.util.Log
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.usecases.usecase14.AndroidVersionDatabase
import com.example.coroutines.usecases.usecase14.AndroidVersionRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CoroutineUsecasesOnAndroidApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    val androidVersionRepository by lazy {
        val database = AndroidVersionDatabase.getInstance(applicationContext).androidVersionDao()
        AndroidVersionRepository(
            database,
            applicationScope
        )
    }

    override fun onCreate() {
        super.onCreate()

        Log.d(TAG, "onCreate: Application starts")

        // Enable Debugging for Kotlin Coroutines in debug builds
        // Prints Coroutine name when logging Thread.currentThread().name
        System.setProperty("kotlinx.coroutines.debug", if (BuildConfig.DEBUG) "on" else "off")
    }
}
package com.example.errorhandlermodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.commonmodule.extensions.addText
import com.example.errorhandlermodule.databinding.ActivityMainErrorHandlingBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class ErrorHandlingMainActivity : AppCompatActivity() {

    private var _binding: ActivityMainErrorHandlingBinding? = null
    private val binding get() = _binding!!
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        addText("Handle $exception in CoroutineExceptionHandler")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainErrorHandlingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {

            tryCatchWithLaunch.setOnClickListener {
                clearTexts()
                tryCatchWithLaunch()
            }

            tryCatchWithNestedLaunch.setOnClickListener {
                clearTexts()
                tryCatchWithNestedLaunch()
            }

            fetchDataWithExHandler.setOnClickListener {
                clearTexts()
                fetchDataWithExHandler()
            }

            asyncAwaitWithExHandler.setOnClickListener {
                clearTexts()
                asyncAwaitWithExHandler()
            }

            tryCatchWithAsyncAwait.setOnClickListener {
                clearTexts()
                tryCatchWithAsyncAwait()
            }

            exHandlingWithCoroutineScope.setOnClickListener {
                clearTexts()
                exHandlingWithCoroutineScope()
            }

            launchSupervisorScopeWithExHandler.setOnClickListener {
                clearTexts()
                launchSupervisorScopeWithExHandler()
            }

            asyncSupervisorScopeWithTryCatch.setOnClickListener {
                clearTexts()
                asyncSupervisorScopeWithTryCatch()
            }
        }
    }

    private fun clearTexts() {
        binding.textContainer.removeAllViews()
    }

    private fun tryCatchWithLaunch() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                throw IllegalStateException("Error thrown in tryCatchWithLaunch")
            } catch (e: Exception) {
                addText("!!! Handle Exception $e")
            }
        }
    }

    private fun tryCatchWithNestedLaunch() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                launch {
                    throw IllegalStateException("Error thrown in tryCatchWithNestedLaunch")
                }
            } catch (e: Exception) {
                addText("!!! Handle Exception $e")
            }
        }
    }

    private fun fetchDataWithExHandler() {
        lifecycleScope.launch(coroutineExceptionHandler) {
            launch {
                throw IllegalStateException("Error thrown in fetchDataWithExHandler")
            }
        }
    }

    private fun asyncAwaitWithExHandler() {
        lifecycleScope.launch(Dispatchers.IO + coroutineExceptionHandler) {
            val deferredResult = lifecycleScope.async {
                throw IllegalStateException("Error thrown in asyncAwaitWithExHandler")
            }

            deferredResult.await()
        }
    }

    private fun tryCatchWithAsyncAwait() {
        lifecycleScope.launch(Dispatchers.IO) {
            val deferredResult = lifecycleScope.async {
                throw IllegalStateException("Error thrown in tryCatchWithAsyncAwait")
            }

            try {
                deferredResult.await()
            } catch (e: Exception) {
                addText("Handle Exception $e")
            }
        }
    }

    private fun exHandlingWithCoroutineScope() {
        lifecycleScope.launch {
            try {
                coroutineScope {
                    launch {
                        throw IllegalStateException("IllegalStateException in nested coroutine")
                    }

                    // ----------- OR We can also write async/await inside coroutineScope
                    val deferredResult1 = async(Dispatchers.IO) {
                        throw IllegalStateException("Error thrown in async")
                    }
                    deferredResult1.await()

                }
            } catch (e: Exception) {
                addText("Handle $e in try/catch")
                // catch error
            }
        }
    }

    private fun launchSupervisorScopeWithExHandler() {
        lifecycleScope.launch(coroutineExceptionHandler) {
            launch {
                addText("starting Coroutine 1")
            }

            supervisorScope {
                launch {
                    addText("starting Coroutine 2")
                    throw java.lang.IllegalStateException("Exception in Coroutine 2 inside supervisor scope")
                }

                launch {
                    addText("starting Coroutine 3")
                }
            }
        }
    }

    private fun asyncSupervisorScopeWithTryCatch() {
        lifecycleScope.launch {
            launch {
                addText("starting Coroutine 1")
            }

            supervisorScope {
                val job4 = async {
                    addText("starting Coroutine 4")
                    throw java.lang.IllegalStateException("Exception in Coroutine 4 inside supervisor scope")
                }

                val job5 = async {
                    addText("starting Coroutine 5")
                    throw java.lang.IllegalStateException("Exception in Coroutine 5 inside supervisor scope")
                }

                try {
                    job4.await()
                } catch (e: Exception) {
                    addText("Handle $e in try/catch")
                }

                try {
                    job5.await()
                } catch (e: Exception) {
                    addText("Handle $e in try/catch")
                }
            }
        }
    }

    private fun addText(output: String) {
        lifecycleScope.launch { binding.textContainer.addText(output) }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
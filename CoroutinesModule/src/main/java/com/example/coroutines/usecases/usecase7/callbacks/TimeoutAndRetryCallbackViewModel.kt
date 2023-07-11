package com.example.coroutines.usecases.usecase7.callbacks

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.coroutines.CoroutinesActivity.Companion.TAG
import com.example.coroutines.base.BaseViewModel
import com.example.coroutines.mock.VersionFeatures
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimeoutAndRetryCallbackViewModel(api: CallbackMockApi = mockApi()) :
    BaseViewModel<UiState>() {

    private val totalAttempts = 3

    private var oreoFeaturesRequestAttemptNumber = 0
    private var pieFeaturesRequestAttemptNumber = 0

    private val shouldRetryOreoFeaturesRequest: Boolean
        get() = oreoFeaturesRequestAttemptNumber < totalAttempts

    private val shouldRetryPieFeaturesRequest: Boolean
        get() = pieFeaturesRequestAttemptNumber < totalAttempts

    private val timeout = 1000L

    private val oreoFeaturesTimeoutHandler = Handler(Looper.getMainLooper())
    private val pieFeaturesTimeoutHandler = Handler(Looper.getMainLooper())

    private var oreoFeaturesCall = api.getAndroidVersionFeatures(27)
    private var pieFeaturesCall = api.getAndroidVersionFeatures(28)

    private var oreoFeaturesResult: VersionFeatures? = null
    private var pieFeaturesResult: VersionFeatures? = null

    private val oreoFeaturesReceived: Boolean
        get() = oreoFeaturesResult != null

    private val pieFeaturesReceived: Boolean
        get() = pieFeaturesResult != null

    fun performNetworkRequest() {
        uiState.value = UiState.Loading

        getOreoFeatures()
        getPieFeatures()
    }

    private fun getOreoFeatures() {
        oreoFeaturesRequestAttemptNumber++
        Log.d(TAG, "getOreoFeatures: Start get oreo features request")
        oreoFeaturesCall = oreoFeaturesCall.clone()
        oreoFeaturesCall.enqueue(object : Callback<VersionFeatures> {
            override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
                Log.d(TAG, "Get oreo features onFailure() entered: $t")
                stopOreoFeaturesTimeoutHandler()
                postErrorOrRetryGetOreoFeatures()
            }

            override fun onResponse(
                call: Call<VersionFeatures>,
                response: Response<VersionFeatures>
            ) {
                stopOreoFeaturesTimeoutHandler()
                if (!response.isSuccessful) {
                    Log.d(TAG, "Get oreo features request was unsuccessful")
                    postErrorOrRetryGetOreoFeatures()
                    return
                } else {
                    Log.d(TAG, "successful oreo response received.")
                }
                oreoFeaturesResult = response.body()
                maybeNotifyView()
            }
        })
        setOreoFeaturesTimeout()
    }

    private fun setOreoFeaturesTimeout() {
        val retryRunnable = Runnable {
            Log.d(TAG, "Timeout for getting oreo features")
            if (!oreoFeaturesReceived) {
                oreoFeaturesCall.cancel()
            }
        }
        Log.d(TAG, "start oreo Features Timeout Handler")
        oreoFeaturesTimeoutHandler.postDelayed(retryRunnable, timeout)
    }

    private fun postErrorOrRetryGetOreoFeatures() {
        if (shouldRetryOreoFeaturesRequest) {
            getOreoFeatures()
        } else {
            uiState.value = UiState.Error("Network Request failed.")
            cancelNetworkRequests()
        }
    }

    private fun getPieFeatures() {
        pieFeaturesRequestAttemptNumber++
        Log.d(TAG, "Start get pie features request")
        pieFeaturesCall = pieFeaturesCall.clone()
        pieFeaturesCall.enqueue(object : Callback<VersionFeatures> {
            override fun onFailure(call: Call<VersionFeatures>, t: Throwable) {
                Log.d(TAG, "Get pie features onFailure() entered: $t")
                stopPieFeaturesTimeoutHandler()
                postErrorOrRetryGetPieFeatures()
            }

            override fun onResponse(
                call: Call<VersionFeatures>,
                response: Response<VersionFeatures>
            ) {
                stopPieFeaturesTimeoutHandler()

                if (!response.isSuccessful) {
                    Log.d(TAG, "Get pie features request was unsuccessful")
                    postErrorOrRetryGetPieFeatures()
                    return
                } else {
                    Log.d(TAG, "successful pie response received.")
                }

                pieFeaturesResult = response.body()
                maybeNotifyView()
            }
        })
        setPieFeaturesTimeout()
    }

    private fun stopPieFeaturesTimeoutHandler() {
        Log.d(TAG, "stopping pie Features Timeout Handler")
        pieFeaturesTimeoutHandler.removeCallbacksAndMessages(null)
    }

    private fun setPieFeaturesTimeout() {
        val retryRunnable = Runnable {
            Log.d(TAG, "Timeout for getting pie features")
            if (!pieFeaturesReceived) {
                pieFeaturesCall.cancel()
            }
        }
        Log.d(TAG, "start pie Features Timeout Handler")
        pieFeaturesTimeoutHandler.postDelayed(retryRunnable, timeout)
    }

    private fun postErrorOrRetryGetPieFeatures() {
        if (shouldRetryPieFeaturesRequest) {
            getPieFeatures()
        } else {
            uiState.value = UiState.Error("Network Request failed.")
            cancelNetworkRequests()
        }
    }

    private fun maybeNotifyView() {
        if (oreoFeaturesReceived && pieFeaturesReceived) {
            uiState.value = UiState.Success(listOf(oreoFeaturesResult!!, pieFeaturesResult!!))
        }
    }

    override fun onCleared() {
        super.onCleared()
        cancelNetworkRequests()
        stopOreoFeaturesTimeoutHandler()
        stopPieFeaturesTimeoutHandler()
    }

    private fun stopOreoFeaturesTimeoutHandler() {
        Log.d(TAG, "stopping oreo Features Timeout Handler")
        oreoFeaturesTimeoutHandler.removeCallbacksAndMessages(null)
    }

    private fun cancelNetworkRequests() {
        oreoFeaturesCall.cancel()
        pieFeaturesCall.cancel()
    }
}
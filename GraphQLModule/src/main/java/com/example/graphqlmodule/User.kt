package com.example.graphqlmodule

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object User {
    private const val KEY_TOKEN = "TOKEN"

    @RequiresApi(Build.VERSION_CODES.M)
    private fun preferences(context: Context): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun getToken(context: Context): String? {
        return preferences(context).getString(KEY_TOKEN, null)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setToken(context: Context, token: String) {
        preferences(context).edit().apply {
            putString(KEY_TOKEN, token)
            apply()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun removeToken(context: Context) {
        preferences(context).edit().apply {
            remove(KEY_TOKEN)
            apply()
        }
    }
}
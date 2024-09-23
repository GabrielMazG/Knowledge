package com.example.knowledge.compose.jetreaderapp.screens.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetreaderapp.model.MUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ReaderLoginViewModel : ViewModel() {

    val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Logger.log(
                                title = "Firebase",
                                message = "signInWithEmailAndPassword: Success - ${task.result}"
                            )
                            home()
                        } else {
                            Logger.log(
                                title = "Firebase",
                                message = "signInWithEmailAndPassword: Failure - ${task.exception?.message}"
                            )
                        }
                    }
            } catch (ex: Exception) {
                Logger.log(title = "Firebase", message = "Error: ${ex.message}")
            }
        }

    fun createUserWithEmailAndPassword(email: String, password: String, home: () -> Unit) {
        if (_loading.value == false) {
            _loading.value = true
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.log(
                            title = "Firebase",
                            message = "createUserWithEmailAndPassword: Success - ${task.result}"
                        )
                        val displayName = task.result?.user?.email?.split("@")?.get(0) ?: ""
                        createUser(displayName)
                        home()
                    } else {
                        Logger.log(
                            title = "Firebase",
                            message = "createUserWithEmailAndPassword: Failure - ${task.exception?.message}"
                        )
                    }
                    _loading.value = false
                }
        }
    }

    private fun createUser(displayName: String) {
        val userId = auth.currentUser?.uid
        val user = MUser(
            id = null,
            userId = userId.toString(),
            displayName = displayName,
            avatarUrl = "",
            quote = "Life is great",
            profession = "Android Developer"
        ).toMap()

        FirebaseFirestore.getInstance().collection("users").add(user)
    }
}
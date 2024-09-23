package com.example.knowledge.compose.jetreaderapp.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.knowledge.R
import com.example.knowledge.compose.jetreaderapp.components.EmailInput
import com.example.knowledge.compose.jetreaderapp.components.PasswordInput
import com.example.knowledge.compose.jetreaderapp.components.ReaderLogo
import com.example.knowledge.compose.jetreaderapp.navigation.ReaderScreens
import com.example.knowledge.compose.theme.ColorAccent
import com.example.knowledge.compose.theme.ColorPrimary
import com.example.knowledge.compose.theme.ColorPrimaryDark
import com.example.knowledge.compose.theme.ColorSecondary
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.White

@Composable
fun LoginScreen(
    navController: NavController,
    viewmodel: ReaderLoginViewModel = viewModel()
) {
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ColorPrimaryDark
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            ReaderLogo()
            if (showLoginForm.value)
                UserForm(loading = false, isCreateAccount = false) { email, password ->
                    viewmodel.signInWithEmailAndPassword(email, password) {
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
                }
            else
                UserForm(loading = false, isCreateAccount = true) { email, password ->
                    viewmodel.createUserWithEmailAndPassword(email, password) {
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
                }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ) {
                if (showLoginForm.value) {
                    Text(
                        text = "New User?",
                        color = ColorSecondaryLight
                    )
                    Text(
                        text = "Sign up",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                showLoginForm.value = !showLoginForm.value
                            },
                        fontWeight = FontWeight.Bold,
                        color = ColorAccent
                    )
                } else {
                    Text(
                        text = "Login",
                        modifier = Modifier
                            .padding(start = 5.dp)
                            .clickable {
                                showLoginForm.value = !showLoginForm.value
                            },
                        fontWeight = FontWeight.Bold,
                        color = ColorAccent
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }
    Surface(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .background(ColorAccent)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isCreateAccount)
                Text(
                    text = stringResource(id = R.string.create_account),
                    modifier = Modifier.padding(12.dp),
                    color = ColorSecondaryLight
                )
            else
                Text(text = "")
            EmailInput(
                emailState = email,
                enabled = !loading,
                onAction = KeyboardActions {
                    passwordFocusRequest.requestFocus()
                })
            PasswordInput(
                modifier = Modifier.focusRequester(passwordFocusRequest),
                passwordState = password,
                labelId = "Password",
                enabled = !loading,
                passwordVisibility = passwordVisibility,
                onAction = KeyboardActions {
                    if (!valid) return@KeyboardActions
                    onDone(email.value.trim(), password.value.trim())
                }
            )
            SubmitButton(
                textId = if (isCreateAccount) "Create Account" else "Login",
                loading = loading,
                validInputs = valid
            ) {
                onDone(email.value.trim(), password.value.trim())
                keyboardController?.hide()
            }
        }
    }
}

@Composable
fun SubmitButton(
    textId: String,
    loading: Boolean,
    validInputs: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(6.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
        colors = buttonColors(
            backgroundColor = ColorSecondary,
            disabledBackgroundColor = ColorSecondaryLight,
        )
    ) {
        if (loading)
            CircularProgressIndicator(
                modifier = Modifier.size(25.dp),
                color = White
            )
        Text(
            text = textId,
            modifier = Modifier.padding(5.dp),
            color = if (validInputs) ColorPrimaryDark else ColorPrimary
        )
    }
}
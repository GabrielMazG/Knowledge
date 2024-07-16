package com.example.knowledge.compose.jettriviaapp.screens

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.knowledge.compose.theme.ColorPrimaryDark
import com.example.knowledge.compose.theme.KnowledgeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class JetTriviaActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Surface(color = ColorPrimaryDark) {
                    val viewModel: QuestionsViewModel by viewModels()
                    TriviaHome(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    KnowledgeTheme {
        content()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp {

    }
}
package com.example.knowledge.compose

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.compose.jetnoteapp.JetNoteActivity
import com.example.knowledge.compose.jettip.JetTipActivity
import com.example.knowledge.compose.jettriviaapp.screens.JetTriviaActivity
import com.example.knowledge.compose.jetweatherforecast.screens.JetWeatherForecastActivity
import com.example.knowledge.compose.movieapp.MovieAppActivity
import com.example.knowledge.compose.profile.ProfileActivity
import com.example.knowledge.databinding.ActivityComposeBinding

class ComposeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityComposeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComposeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            profile.setOnClickListener {
                startActivity(Intent(this@ComposeActivity, ProfileActivity::class.java))
            }
            jetTip.setOnClickListener {
                startActivity(Intent(this@ComposeActivity, JetTipActivity::class.java))
            }
            movieApp.setOnClickListener {
                startActivity(Intent(this@ComposeActivity, MovieAppActivity::class.java))
            }
            jetNoteApp.setOnClickListener {
                startActivity(Intent(this@ComposeActivity, JetNoteActivity::class.java))
            }
            jetTriviaApp.setOnClickListener {
                startActivity(Intent(this@ComposeActivity, JetTriviaActivity::class.java))
            }
            jetWeatherForecastApp.setOnClickListener {
                startActivity(Intent(this@ComposeActivity, JetWeatherForecastActivity::class.java))
            }
        }
    }
}
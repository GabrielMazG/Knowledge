package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.coroutines.CoroutinesActivity
import com.example.coroutinescodelabmodule.main.CoroutinesCodelabActivity
import com.example.errorhandlermodule.ErrorHandlingMainActivity
import com.example.flowcodelabmodule.ui.FlowCodelabActivity
import com.example.flowmodule.FlowActivity
import com.example.graphqlmodule.GraphQLMainActivity
import com.example.knowledge.compose.ComposeActivity
import com.example.knowledge.databinding.ActivityMainBinding
import com.example.navigationcomponentmodule.NavigationComponentMainActivity
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        FirebaseApp.initializeApp(this)

        with(binding) {
            navigationComponents.setOnClickListener {
                startActivity(
                    Intent(
                        this@MainActivity,
                        NavigationComponentMainActivity::class.java
                    )
                )
            }

            flows.setOnClickListener {
                startActivity(Intent(this@MainActivity, FlowActivity::class.java))
            }

            flowsCodelab.setOnClickListener {
                startActivity(Intent(this@MainActivity, FlowCodelabActivity::class.java))
            }

            graphQL.setOnClickListener {
                startActivity(Intent(this@MainActivity, GraphQLMainActivity::class.java))
            }

            errorHandling.setOnClickListener {
                startActivity(Intent(this@MainActivity, ErrorHandlingMainActivity::class.java))
            }

            compose.setOnClickListener {
                startActivity(Intent(this@MainActivity, ComposeActivity::class.java))
            }

            coroutines.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutinesActivity::class.java))
            }

            coroutinesCodelab.setOnClickListener {
                startActivity(Intent(this@MainActivity, CoroutinesCodelabActivity::class.java))
            }
        }
    }
}
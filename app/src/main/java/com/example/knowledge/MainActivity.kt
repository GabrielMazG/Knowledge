package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.composemodule.ComposeActivity
import com.example.errorhandlermodule.ErrorHandlingMainActivity
import com.example.flowmodule.FlowActivity
import com.example.graphqlmodule.GraphQLMainActivity
import com.example.knowledge.databinding.ActivityMainBinding
import com.example.navigationcomponentmodule.NavigationComponentMainActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

            graphQL.setOnClickListener {
                startActivity(Intent(this@MainActivity, GraphQLMainActivity::class.java))
            }

            errorHandling.setOnClickListener {
                startActivity(Intent(this@MainActivity, ErrorHandlingMainActivity::class.java))
            }

            compose.setOnClickListener {
                startActivity(Intent(this@MainActivity, ComposeActivity::class.java))
            }
        }
    }
}
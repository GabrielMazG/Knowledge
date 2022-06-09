package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.databinding.ActivityMainBinding
import com.example.knowledge.flow.FlowMainActivity
import com.example.knowledge.graphQL.GraphQLMainActivity
import com.example.knowledge.navigationComponent.NavigationComponentMainActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.navigationComponents.setOnClickListener {
            startActivity(Intent(this, NavigationComponentMainActivity::class.java))
        }

        binding.flows.setOnClickListener {
            startActivity(Intent(this, FlowMainActivity::class.java))
        }

        binding.graphQL.setOnClickListener {
            startActivity(Intent(this, GraphQLMainActivity::class.java))
        }
    }
}
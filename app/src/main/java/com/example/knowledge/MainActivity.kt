package com.example.knowledge

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.knowledge.databinding.ActivityMainBinding
import com.example.knowledge.navigationComponent.MainActivityNavigationComponent

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.navigationComponents.setOnClickListener {
            startActivity(Intent(this, MainActivityNavigationComponent::class.java))
        }
    }
}
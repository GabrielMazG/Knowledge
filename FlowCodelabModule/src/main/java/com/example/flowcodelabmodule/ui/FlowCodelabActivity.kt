package com.example.flowcodelabmodule.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flowcodelabmodule.databinding.ActivityFlowcodelabBinding

class FlowCodelabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowcodelabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowcodelabBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}

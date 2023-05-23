package com.example.composemodule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.composemodule.databinding.ActivityComposeBinding
import com.example.composemodule.profile.ProfileActivity

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
        }
    }
}
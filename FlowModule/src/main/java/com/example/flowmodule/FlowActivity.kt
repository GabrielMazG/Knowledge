package com.example.flowmodule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.flowmodule.compare.FlowCompareActivity
import com.example.flowmodule.databinding.ActivityFlowBinding
import com.example.flowmodule.examples.FlowExample1Activity
import com.example.flowmodule.examples.FlowExample2Activity
import com.example.flowmodule.examples.FlowExample3Activity
import com.example.flowmodule.examples.FlowExample4Activity
import com.example.flowmodule.examples.FlowExample5Activity
import com.example.flowmodule.stockapp.usecase1.FlowUseCase1Activity
import com.example.flowmodule.stockapp.usecase2.FlowUseCase2Activity
import com.example.flowmodule.stockapp.usecase3.FlowUseCase3Activity
import com.example.flowmodule.stockapp.usecase4.FlowUseCase4Activity

class FlowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFlowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        with(binding) {
            compare.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowCompareActivity::class.java))
            }
            example1.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowExample1Activity::class.java))
            }
            example2.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowExample2Activity::class.java))
            }
            example3.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowExample3Activity::class.java))
            }
            example4.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowExample4Activity::class.java))
            }
            example5.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowExample5Activity::class.java))
            }
            stockApp.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowUseCase1Activity::class.java))
            }
            stockApp2.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowUseCase2Activity::class.java))
            }
            stockApp3.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowUseCase3Activity::class.java))
            }
            stockApp4.setOnClickListener {
                startActivity(Intent(this@FlowActivity, FlowUseCase4Activity::class.java))
            }
        }
    }

    companion object {
        const val TAG = "FLOW TAG"
    }
}
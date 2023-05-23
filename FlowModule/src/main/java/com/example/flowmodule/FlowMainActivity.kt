package com.example.flowmodule

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.flowmodule.databinding.ActivityMainFlowBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FlowMainActivity : AppCompatActivity() {

    private var _binding: ActivityMainFlowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FlowMainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainFlowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.liveData.setOnClickListener { viewModel.triggerLiveData() }
        binding.stateFlow.setOnClickListener { viewModel.triggerStateFlow() }
        binding.flow.setOnClickListener {
            lifecycleScope.launch {
                viewModel.triggerFlow().collectLatest {
                    binding.flowTitle.text = it
                }

            }
        }
        binding.sharedFlow.setOnClickListener { viewModel.triggerSharedFlow() }

        subscribeToObservables()
    }

    private fun subscribeToObservables() {
        viewModel.liveData.observe(this) {
            binding.liveDataTitle.text = it
        }

        lifecycleScope.launchWhenStarted {
            // On rotation StateFlow emit again the result, it will show the SnackBar, but on click
            // it will not show the SnackBar
            viewModel.stateFlow.collectLatest {
                binding.stateFlowTitle.text = it
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        lifecycleScope.launchWhenStarted {
            // The shared flow will show the SnackBar every time the button is clicked,
            // even in rotation
            viewModel.sharedFlow.collectLatest {
                binding.sharedFlowTitle.text = it
                Snackbar.make(
                    binding.root,
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
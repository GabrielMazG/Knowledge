package com.example.coroutines.usecases.usecase15

import android.os.Bundle
import androidx.activity.viewModels
import com.example.coroutines.base.BaseActivity
import com.example.coroutines.base.useCase15Description
import com.example.coroutines.databinding.ActivityWorkmangerBinding

class WorkManagerActivity : BaseActivity() {

    override fun getToolbarTitle() = useCase15Description

    private val binding by lazy { ActivityWorkmangerBinding.inflate(layoutInflater) }
    private val viewModel: WorkManagerViewModel by viewModels {
        ViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel.performAnalyticsRequest()
    }
}
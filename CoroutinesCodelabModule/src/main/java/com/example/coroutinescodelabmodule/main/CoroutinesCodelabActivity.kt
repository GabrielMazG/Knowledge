/*
 * Copyright (C) 2019 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.coroutinescodelabmodule.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.coroutinescodelabmodule.databinding.ActivityCoroutinesCodelabBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Show layout.activity_main and setup data binding.
 */
class CoroutinesCodelabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCoroutinesCodelabBinding

    /**
     * Inflate layout.activity_main and setup data binding.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCoroutinesCodelabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get MainViewModel by passing a database to the factory
        val database = getDatabase(this)
        val repository = TitleRepository(getNetworkService(), database.titleDao)
        val viewModel = ViewModelProvider(
            this,
            CoroutinesCodelabViewModel.FACTORY(repository)
        )[CoroutinesCodelabViewModel::class.java]

        with(binding) {

            // When rootLayout is clicked call onMainViewClicked in ViewModel
            rootLayout.setOnClickListener {
                viewModel.onMainViewClicked()
            }

            // update the title when the [MainViewModel.title] changes
            viewModel.title.observe(this@CoroutinesCodelabActivity) { value ->
                value?.let {
                    title.text = it
                }
            }

            viewModel.taps.observe(this@CoroutinesCodelabActivity) { value ->
                taps.text = value
            }

            // show the spinner when [MainViewModel.spinner] is true
            viewModel.spinner.observe(this@CoroutinesCodelabActivity) { value ->
                value.let { show ->
                    spinner.visibility = if (show) View.VISIBLE else View.GONE
                }
            }

            // Show a snackbar whenever the [ViewModel.snackbar] is updated a non-null value
            viewModel.snackbar.observe(this@CoroutinesCodelabActivity) { text ->
                text?.let {
                    Snackbar.make(rootLayout, text, Snackbar.LENGTH_SHORT).show()
                    viewModel.onSnackbarShown()
                }
            }
        }
    }
}

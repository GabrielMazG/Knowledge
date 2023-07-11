package com.example.coroutines

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutines.base.BaseActivity
import com.example.coroutines.base.UseCase
import com.example.coroutines.base.UseCaseAdapter
import com.example.coroutines.base.coroutinesUseCases
import com.example.coroutines.databinding.ActivityCoroutinesBinding

class CoroutinesActivity : BaseActivity() {

    private val onUseCaseClickListener: (UseCase) -> Unit = { clickedUseCase ->
        startActivity(Intent(applicationContext, clickedUseCase.targetActivity))
    }

    private lateinit var binding: ActivityCoroutinesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            adapter =
                UseCaseAdapter(
                    coroutinesUseCases,
                    onUseCaseClickListener
                )
            hasFixedSize()
            layoutManager = LinearLayoutManager(this@CoroutinesActivity)
            addItemDecoration(initItemDecoration())
        }
    }

    private fun initItemDecoration(): DividerItemDecoration {
        val itemDecorator =
            DividerItemDecoration(applicationContext, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(
            ContextCompat.getDrawable(
                applicationContext,
                R.drawable.recyclerview_divider
            )!!
        )
        return itemDecorator
    }

    override fun getToolbarTitle() = "Coroutines and Flows on Android"

    companion object {
        const val TAG = "COROUTINE TAG"
    }
}
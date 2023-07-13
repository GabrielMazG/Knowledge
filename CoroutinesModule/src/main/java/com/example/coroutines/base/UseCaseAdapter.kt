package com.example.coroutines.base

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.coroutines.R

class UseCaseAdapter(
    private val useCaseCategory: UseCaseCategory,
    private val onUseCaseClick: (UseCase) -> Unit
) : RecyclerView.Adapter<UseCaseAdapter.ViewHolder>() {

    class ViewHolder(val button: Button) : RecyclerView.ViewHolder(button)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val button = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_item, parent, false) as Button
        return ViewHolder(button)
    }

    override fun getItemCount() = useCaseCategory.useCases.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.button.text = useCaseCategory.useCases[position].description
        holder.button.setOnClickListener {
            onUseCaseClick(useCaseCategory.useCases[position])
        }
    }
}
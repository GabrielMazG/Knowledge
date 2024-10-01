package com.example.knowledge.compose.jetreaderapp.screens.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetreaderapp.data.Resource
import com.example.knowledge.compose.jetreaderapp.model.Item
import com.example.knowledge.compose.jetreaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReaderBookSearchViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {

    var list: List<Item> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

    private fun loadBooks() {
        searchBooks("flutter")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) return@launch

            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data ?: listOf()
                        if (list.isNotEmpty()) isLoading = false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        Logger.log(title = "searchBooks", message = response.message.toString())
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                isLoading = false
                Logger.log(title = "searchBooks", message = e.message.toString())
            }
        }
    }
}
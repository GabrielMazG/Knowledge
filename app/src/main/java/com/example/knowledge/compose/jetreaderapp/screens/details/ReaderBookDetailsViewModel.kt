package com.example.knowledge.compose.jetreaderapp.screens.details

import androidx.lifecycle.ViewModel
import com.example.knowledge.compose.jetreaderapp.data.Resource
import com.example.knowledge.compose.jetreaderapp.model.Item
import com.example.knowledge.compose.jetreaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ReaderBookDetailsViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {

    suspend fun getBookInfo(bookId: String): Resource<Item> =
        repository.getBookInfo(bookId = bookId)
}
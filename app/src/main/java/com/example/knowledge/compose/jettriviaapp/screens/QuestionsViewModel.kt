package com.example.knowledge.compose.jettriviaapp.screens

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowledge.compose.jettriviaapp.data.DataOrException
import com.example.knowledge.compose.jettriviaapp.model.QuestionItem
import com.example.knowledge.compose.jettriviaapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(private val repository: QuestionRepository) :
    ViewModel() {

    val data: MutableState<DataOrException<ArrayList<QuestionItem>, Boolean, Exception>> =
        mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            val result = repository.getAllQuestions()
            result.data = result.data?.take(20) as ArrayList<QuestionItem>?
            data.value = result
            if (data.value.data.toString().isNotEmpty())
                data.value.loading = false
        }
    }

    fun getTotalQuestionCount(): Int = data.value.data?.toMutableList()?.size ?: 0
}
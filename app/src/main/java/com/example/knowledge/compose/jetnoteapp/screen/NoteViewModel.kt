package com.example.knowledge.compose.jetnoteapp.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.knowledge.compose.jetnoteapp.data.NotesDataSource
import com.example.knowledge.compose.jetnoteapp.model.Note

@RequiresApi(Build.VERSION_CODES.O)
class NoteViewModel : ViewModel() {
    private val noteList = mutableStateListOf<Note>()

    init {
        noteList.addAll(NotesDataSource().loadNotes())
    }

    fun addNote(note: Note) {
        noteList.add(note)
    }

    fun removeNote(note: Note) {
        noteList.remove(note)
    }

    fun getAllNotes(): List<Note> = noteList
}
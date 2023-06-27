package com.example.composemodule.jetnoteapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.composemodule.jetnoteapp.model.Note

class NotesDataSource {
    @RequiresApi(Build.VERSION_CODES.O)
    fun loadNotes(): List<Note> = listOf(
        Note(
            title = "Title1",
            description = "Description1"
        ),
        Note(
            title = "Title2",
            description = "Description2"
        ),
        Note(
            title = "Title3",
            description = "Description3"
        ),
        Note(
            title = "Title4",
            description = "Description4"
        ),
        Note(
            title = "Title5",
            description = "Description5"
        ),
        Note(
            title = "Title6",
            description = "Description6"
        ),
        Note(
            title = "Title7",
            description = "Description7"
        )
    )
}
package com.example.composemodule.jetnoteapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.composemodule.jetnoteapp.data.NotesDataSource
import com.example.composemodule.jetnoteapp.screen.NoteScreen
import com.example.composemodule.jetnoteapp.screen.NoteViewModel
import com.example.composemodule.theme.ColorPrimaryDark
import com.example.composemodule.theme.KnowledgeTheme

class JetNoteActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Surface(color = ColorPrimaryDark) {
                    val viewModel: NoteViewModel by viewModels()
                    NotesApp(viewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotesApp(noteViewModel: NoteViewModel = viewModel()) {
    val notesList = noteViewModel.getAllNotes()

    NoteScreen(
        notes = notesList,
        onAddNote = { note -> noteViewModel.addNote(note) },
        onRemoveNote = { note -> noteViewModel.removeNote(note) }
    )
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    KnowledgeTheme {
        content()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApp {
        NoteScreen(
            notes = NotesDataSource().loadNotes(),
            onAddNote = {},
            onRemoveNote = {}
        )
    }
}
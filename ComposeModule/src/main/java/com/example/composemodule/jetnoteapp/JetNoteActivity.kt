package com.example.composemodule.jetnoteapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.example.composemodule.jetnoteapp.data.NotesDataSource
import com.example.composemodule.jetnoteapp.model.Note
import com.example.composemodule.jetnoteapp.screen.NoteScreen
import com.example.composemodule.theme.ColorPrimaryDark
import com.example.composemodule.theme.KnowledgeTheme

class JetNoteActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Surface(color = ColorPrimaryDark) {
                    val notes = remember {
                        mutableListOf<Note>()
                    }
                    NoteScreen(
                        notes = notes,
                        onAddNote = { note ->
                            notes.add(note)
                        },
                        onRemoveNote = { note ->
                            notes.remove(note)
                        }
                    )
                }
            }
        }
    }
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
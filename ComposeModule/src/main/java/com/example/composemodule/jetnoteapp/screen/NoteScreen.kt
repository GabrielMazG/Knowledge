package com.example.composemodule.jetnoteapp.screen

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composemodule.R
import com.example.composemodule.jetnoteapp.components.NoteButton
import com.example.composemodule.jetnoteapp.components.NoteInputText
import com.example.composemodule.jetnoteapp.data.NotesDataSource
import com.example.composemodule.jetnoteapp.model.Note
import com.example.composemodule.theme.ColorAccent
import com.example.composemodule.theme.ColorSecondaryLight
import com.example.composemodule.theme.Typography
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteScreen(
    notes: List<Note>,
    onAddNote: (Note) -> Unit,
    onRemoveNote: (Note) -> Unit
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Column(modifier = Modifier.padding(6.dp)) {
        // Top bar
        TopAppBar(
            title = {
                Text(text = stringResource(id = R.string.title_jetnoteapp))
            },
            actions = {
                Icon(imageVector = Icons.Rounded.Notifications, contentDescription = "Icon")
            },
            backgroundColor = ColorAccent
        )

        // Content
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NoteInputText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = title,
                label = "Title",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) title = it
                }
            )
            NoteInputText(
                modifier = Modifier.padding(vertical = 8.dp),
                text = description,
                label = "Description",
                onTextChange = {
                    if (it.all { char -> char.isLetter() || char.isWhitespace() }) description = it
                }
            )
            NoteButton(text = "Save", onClick = {
                if (title.isNotEmpty() && description.isNotEmpty()) {
                    onAddNote(Note(title = title, description = description))
                    title = ""
                    description = ""
                    Toast.makeText(context, "Note Added", Toast.LENGTH_LONG).show()
                }
            })
        }
        Divider(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 3.dp
            ),
            color = ColorSecondaryLight,
            thickness = 0.2.dp
        )
        LazyColumn {
            items(notes) { note ->
                NoteRow(
                    note = note,
                    onNoteClicked = {
                        onRemoveNote(it)
                    }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NotesScreenPreview() {
    NoteScreen(
        notes = NotesDataSource().loadNotes(),
        onAddNote = {},
        onRemoveNote = {}
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: Note,
    onNoteClicked: (Note) -> Unit
) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = ColorAccent,
        elevation = 6.dp
    ) {
        Column(
            modifier
                .clickable {
                    onNoteClicked(note)
                }
                .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            Text(
                modifier = modifier.padding(),
                text = note.title,
                style = Typography.subtitle2
            )
            Text(
                modifier = modifier.padding(6.dp),
                text = note.description,
                style = Typography.subtitle1
            )
            Text(
                modifier = modifier.padding(6.dp),
                text = note.entryDate.format(DateTimeFormatter.ofPattern("EEE, d MMM")),
                style = Typography.caption
            )
        }
    }
}
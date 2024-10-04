package com.example.knowledge.compose.jetreaderapp.screens.update

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.knowledge.R
import com.example.knowledge.compose.jetreaderapp.components.InputField
import com.example.knowledge.compose.jetreaderapp.components.RatingBar
import com.example.knowledge.compose.jetreaderapp.components.ReaderAppBar
import com.example.knowledge.compose.jetreaderapp.components.showToast
import com.example.knowledge.compose.jetreaderapp.data.DataOrException
import com.example.knowledge.compose.jetreaderapp.model.MBook
import com.example.knowledge.compose.jetreaderapp.navigation.ReaderScreens
import com.example.knowledge.compose.jetreaderapp.screens.home.ReaderHomeViewModel
import com.example.knowledge.compose.jetreaderapp.screens.home.RoundedButton
import com.example.knowledge.compose.jetreaderapp.utils.formatDate
import com.example.knowledge.compose.theme.ColorAccent
import com.example.knowledge.compose.theme.ColorSecondary
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.Typography
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BookUpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: ReaderHomeViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Update Book",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        it
        val bookInfo = produceState<DataOrException<List<MBook>, Boolean, Exception>>(
            initialValue = DataOrException(
                listOf(), true, Exception("")
            )
        ) {
            value = viewModel.data.value
        }.value
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (bookInfo.loading == true) {
                    LinearProgressIndicator()
                    bookInfo.loading = false
                } else {
                    Surface(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        color = ColorSecondaryLight,
                        elevation = 4.dp
                    ) {
                        ShowBookUpdate(bookInfo = viewModel.data.value, bookItemId = bookItemId)
                    }
                    ShowSimpleForm(book = viewModel.data.value.data?.first { mBook ->
                        mBook.googleBookId == bookItemId
                    }!!, navController)

                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ShowSimpleForm(book: MBook, navController: NavHostController) {
    val context = LocalContext.current
    val notesText =
        remember { mutableStateOf(book.notes.toString().ifEmpty { "No thoughts available." }) }
    val isStartedReading = remember { mutableStateOf(book.startedReading.toString().isNotEmpty()) }
    val isFinishedReading =
        remember { mutableStateOf(book.finishedReading.toString().isNotEmpty()) }
    val ratingVal = remember { mutableIntStateOf(book.rating?.toInt() ?: 0) }

    SimpleForm(textFieldValue = notesText) { note ->
        notesText.value = note
    }
    Row(
        modifier = Modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        TextButton(
            onClick = { isStartedReading.value = true }, enabled = book.startedReading == null
        ) {
            if (book.startedReading == null) {
                if (!isStartedReading.value) Text(
                    text = "Start Reading", color = ColorSecondary
                )
                else Text(
                    text = "Started Reading!",
                    modifier = Modifier.alpha(0.9f),
                    color = ColorSecondary.copy(alpha = 0.9f)
                )

            } else {
                Text(
                    text = "Started on: ${formatDate(book.startedReading!!)}",
                    modifier = Modifier.alpha(0.6f),
                    color = ColorSecondaryLight
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(
            onClick = { isFinishedReading.value = true }, enabled = book.finishedReading == null
        ) {
            if (book.finishedReading == null) {
                if (!isFinishedReading.value) Text(
                    text = "Mark as read", color = ColorSecondary
                )
                else Text(
                    text = "Finished Reading!",
                    modifier = Modifier.alpha(0.9f),
                    color = ColorSecondary.copy(alpha = 0.9f)
                )
            } else {
                Text(
                    text = "Finished on: ${formatDate(book.finishedReading!!)}",
                    modifier = Modifier.alpha(0.6f),
                    color = ColorSecondaryLight.copy(alpha = 0.5f)
                )
            }
        }
    }
    Text(text = "Rating", modifier = Modifier.padding(bottom = 3.dp))
    book.rating?.toInt().let {
        RatingBar(rating = it ?: 0) { rating ->
            ratingVal.intValue = rating
        }
    }

    Spacer(modifier = Modifier.padding(bottom = 15.dp))
    Row(horizontalArrangement = Arrangement.SpaceBetween) {

        val changedNotes = book.notes != notesText.value
        val changedRating = book.rating?.toInt() != ratingVal.intValue
        val isStartedTimeStamp =
            if (isStartedReading.value) Timestamp.now() else book.startedReading
        val isFinishedTimeStamp =
            if (isFinishedReading.value) Timestamp.now() else book.finishedReading
        val bookUpdate =
            changedNotes || changedRating || isStartedReading.value || isFinishedReading.value

        RoundedButton(label = "Update") {
            if (bookUpdate) {
                val bookToUpdate = hashMapOf(
                    "finished_reading_at" to isFinishedTimeStamp,
                    "started_reading_at" to isStartedTimeStamp,
                    "rating" to ratingVal.intValue,
                    "notes" to notesText.value
                ).toMap()
                FirebaseFirestore.getInstance().collection("books").document(book.id.toString())
                    .update(bookToUpdate).addOnCompleteListener {
                        showToast(context, "Book updated successfully")
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }.addOnFailureListener { }
            }
        }
        Spacer(modifier = Modifier.width(100.dp))
        val openDialog = remember { mutableStateOf(false) }
        if (openDialog.value) {
            ShowAlertDialog(
                message = stringResource(id = R.string.delete_confirmation) + "\n" + stringResource(
                    id = R.string.delete_confirmation_action
                ),
                openDialog = openDialog
            ) {
                FirebaseFirestore.getInstance()
                    .collection("books")
                    .document(book.id.toString())
                    .delete()
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            openDialog.value = false
                            navController.navigate(ReaderScreens.HomeScreen.name)
                        }
                    }
            }
        }
        RoundedButton(label = "Delete") {
            openDialog.value = true
        }
    }
}

@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    textFieldValue: MutableState<String>,
    onSearch: (String) -> Unit
) {
    Column {
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldValue.value) { textFieldValue.value.trim().isNotEmpty() }
        InputField(
            modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(8.dp)
                .background(ColorAccent, RoundedCornerShape(12.dp))
                .padding(horizontal = 20.dp, vertical = 12.dp),
            valueState = textFieldValue,
            labelId = "Enter your thoughts",
            enabled = true,
            onAction = KeyboardActions {
                if (!valid) return@KeyboardActions
                onSearch(textFieldValue.value.trim())
                keyboardController?.hide()
            })
    }
}

@Composable
fun ShowBookUpdate(
    bookInfo: DataOrException<List<MBook>, Boolean, Exception>, bookItemId: String
) {
    Row {
        if (bookInfo.data != null) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                CardListItem(book = bookInfo.data!!.first { mBook ->
                    mBook.googleBookId == bookItemId
                }, onPressDetail = {})
            }
        }
    }
}

@Composable
fun CardListItem(book: MBook, onPressDetail: () -> Unit) {
    Card(
        modifier = Modifier.clickable { onPressDetail() },
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberImagePainter(data = book.photoUrl.toString()),
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp
                        )
                    )
            )
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = book.title.toString(),
                    style = Typography.h6,
                    modifier = Modifier
                        .padding(
                            start = 8.dp, end = 8.dp
                        )
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = book.authors.toString(),
                    style = Typography.body2,
                    modifier = Modifier.padding(
                        start = 8.dp, end = 8.dp, top = 2.dp, bottom = 0.dp
                    )
                )
                Text(
                    text = book.publishedDate.toString(),
                    style = Typography.body2,
                    modifier = Modifier.padding(
                        start = 8.dp, end = 8.dp, top = 0.dp, bottom = 8.dp
                    )
                )
            }
        }
    }
}

@Composable
fun ShowAlertDialog(
    message: String,
    openDialog: MutableState<Boolean>,
    onYesPressed: () -> Unit
) {
    if (openDialog.value) {
        AlertDialog(onDismissRequest = { openDialog.value = false },
            title = { Text(text = "Delete Book") },
            text = { Text(text = message) },
            buttons = {
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { onYesPressed.invoke() }) {
                        Text(text = "Yes")
                    }
                    TextButton(onClick = { openDialog.value = false }) {
                        Text(text = "No")
                    }
                }
            })
    }
}
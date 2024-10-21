package com.example.knowledge.compose.jetreaderapp.screens.stats

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.knowledge.compose.jetreaderapp.components.ReaderAppBar
import com.example.knowledge.compose.jetreaderapp.model.MBook
import com.example.knowledge.compose.jetreaderapp.screens.home.ReaderHomeViewModel
import com.example.knowledge.compose.jetreaderapp.utils.formatDate
import com.example.knowledge.compose.theme.ColorAccent
import com.example.knowledge.compose.theme.ColorPrimaryDark
import com.example.knowledge.compose.theme.ColorSecondary
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.Typography
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import java.util.Date

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun StatsScreen(navController: NavController, viewModel: ReaderHomeViewModel = hiltViewModel()) {

    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Stats",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        it
        books = if (!viewModel.data.value.data.isNullOrEmpty()) {
            viewModel.data.value.data!!.filter { mBook -> (mBook.userId == currentUser?.uid) }
        } else {
            emptyList()
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Sharp.Person,
                    contentDescription = "Icon",
                    tint = ColorSecondary
                )
                // paul @ me.com
                Text(
                    text = "Hi ${currentUser?.email.toString().split("@")[0]}",
                    modifier = Modifier.padding(8.dp),
                    style = Typography.h5,
                    color = ColorSecondary
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                shape = RoundedCornerShape(5.dp),
                elevation = 5.dp,
                backgroundColor = ColorAccent
            ) {
                val readBooksList: List<MBook> =
                    if (!viewModel.data.value.data.isNullOrEmpty()) {
                        books.filter { mBook ->
                            (mBook.userId == currentUser?.uid && mBook.finishedReading != null)
                        }
                    } else {
                        emptyList()
                    }
                val readingBooks =
                    books.filter { mBook -> (mBook.startedReading != null && mBook.finishedReading == null) }

                Column(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Stats",
                        style = Typography.h5,
                        color = ColorSecondary,
                    )
                    Divider(
                        color = ColorSecondaryLight,
                        modifier = Modifier.padding(vertical = 6.dp)
                    )
                    Text(
                        text = "You're reading: ${readingBooks.size} books",
                        color = ColorPrimaryDark,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "You've read: ${readBooksList.size} books",
                        color = ColorPrimaryDark,
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            if (viewModel.data.value.loading == true) {
                LinearProgressIndicator()
            } else {
                Divider(
                    color = ColorSecondary,
                    modifier = Modifier.padding(top = 16.dp, start = 12.dp, end = 12.dp)
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    // Filter books by finished ones
                    val readBooks: List<MBook> =
                        if (!viewModel.data.value.data.isNullOrEmpty()) {
                            books.filter { mBook -> (mBook.userId == currentUser?.uid && mBook.finishedReading != null) }
                        } else {
                            emptyList()
                        }
                    items(items = readBooks) { book ->
                        BookRowStats(book = book)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun BookRowStats(book: MBook) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(6.dp),
        elevation = 7.dp,
        backgroundColor = ColorSecondaryLight
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top
        ) {
            val imageUrl: String = try {
                book.photoUrl.toString()
            } catch (e: Exception) {
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"
            }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "Book image",
                Modifier
                    .width(80.dp)
                    .fillMaxHeight()
            )
            Column {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = book.title.toString(),
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 2.dp),
                        overflow = TextOverflow.Ellipsis,
                        color = ColorAccent,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                    )
                    if ((book.rating?.toInt() ?: 0) >= 4) {
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Thumb Up",
                            tint = ColorPrimaryDark,
                            modifier = Modifier.size(20.dp)
                        )
                    } else {
                        Box {}
                    }
                }
                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    color = ColorPrimaryDark,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
                Text(
                    text = "Started: ${formatDate(book.startedReading ?: Timestamp(Date()))}",
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    color = ColorPrimaryDark,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
                Text(
                    text = "Finished: ${formatDate(book.finishedReading ?: Timestamp(Date()))}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    color = ColorPrimaryDark,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            }
        }
    }
}
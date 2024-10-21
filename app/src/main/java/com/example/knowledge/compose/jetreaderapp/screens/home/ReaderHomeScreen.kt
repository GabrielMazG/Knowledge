package com.example.knowledge.compose.jetreaderapp.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.knowledge.compose.jetreaderapp.components.FABContent
import com.example.knowledge.compose.jetreaderapp.components.ListCard
import com.example.knowledge.compose.jetreaderapp.components.ReaderAppBar
import com.example.knowledge.compose.jetreaderapp.components.TitleSection
import com.example.knowledge.compose.jetreaderapp.model.MBook
import com.example.knowledge.compose.jetreaderapp.navigation.ReaderScreens
import com.example.knowledge.compose.theme.ColorSecondary
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.Typography
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(navController: NavController, viewModel: ReaderHomeViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            ReaderAppBar(title = "A.Reader", navController = navController)
        },
        floatingActionButton = {
            FABContent {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) {
        it
        Surface(modifier = Modifier.fillMaxSize()) {
            HomeContent(navController, viewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavController, viewModel: ReaderHomeViewModel) {
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()) {
        listOfBooks = viewModel.data.value.data!!.toList().filter { book ->
            book.userId == currentUser?.uid.toString()
        }
    }

    val currentUserName =
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
            FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0).toString()
        else
            "N/A"
    Column(
        modifier = Modifier.padding(2.dp),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.align(alignment = Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TitleSection(label = "You are reading \n activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    navController.navigate(ReaderScreens.StatsScreen.name)

                }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier.size(45.dp),
                    tint = ColorSecondary
                )
                Text(
                    text = currentUserName,
                    modifier = Modifier.padding(
                        bottom = 8.dp,
                        top = 2.dp,
                        start = 2.dp,
                        end = 2.dp
                    ),
                    style = Typography.overline,
                    color = ColorSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Divider(
                    color = ColorSecondaryLight,
                    thickness = 1.dp,
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                )
            }
        }
        ReadingRightNowArea(books = listOfBooks, navController = navController)
        TitleSection(label = "Reading List")
        BookListArea(books = listOfBooks, navController = navController)
    }
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController) {
    val addedBooks = books.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }
    HorizontalScrollableComponent(addedBooks) {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun BookListArea(books: List<MBook>, navController: NavController) {
    val addedBooks = books.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }
    HorizontalScrollableComponent(addedBooks) {
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }
}

@Composable
fun HorizontalScrollableComponent(
    listOfBooks: List<MBook>,
    viewModel: ReaderHomeViewModel = hiltViewModel(),
    onCardPressed: (String) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(280.dp)
            .horizontalScroll(scrollState)
    ) {
        if (viewModel.data.value.loading == true) {
            LinearProgressIndicator()
        } else {
            if (listOfBooks.isEmpty()) {
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(
                        text = "No books found. Add a book.",
                        style = TextStyle(
                            color = ColorSecondary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )

                }
            } else {
                for (book in listOfBooks) {
                    ListCard(book) {
                        onCardPressed(book.googleBookId.toString())
                    }
                }
            }
        }
    }
}
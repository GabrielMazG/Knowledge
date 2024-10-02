package com.example.knowledge.compose.jetreaderapp.screens.details

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetreaderapp.components.ReaderAppBar
import com.example.knowledge.compose.jetreaderapp.data.Resource
import com.example.knowledge.compose.jetreaderapp.model.Item
import com.example.knowledge.compose.jetreaderapp.model.MBook
import com.example.knowledge.compose.jetreaderapp.navigation.ReaderScreens
import com.example.knowledge.compose.jetreaderapp.screens.home.RoundedButton
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.Typography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun BookDetailsScreen(
    navController: NavController,
    bookId: String,
    viewModel: ReaderBookDetailsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Book Details",
                showProfile = false,
                icon = Icons.Default.ArrowBack,
                navController = navController
            ) {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }
    ) {
        it
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId = bookId)
                }.value

                if (bookInfo.data == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Loading...", modifier = Modifier.padding(bottom = 6.dp))
                        LinearProgressIndicator()
                    }
                } else {
                    ShowBookDetails(bookInfo, navController)
                }
            }
        }
    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Card(
        modifier = Modifier
            .padding(top = 34.dp, bottom = 20.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        backgroundColor = ColorSecondaryLight
    ) {
        Image(
            painter = rememberImagePainter(data = bookData?.imageLinks?.thumbnail.toString()),
            contentDescription = "Book Image",
            modifier = Modifier
                .width(210.dp)
                .height(210.dp)
                .padding(10.dp)
        )
    }
    Text(
        text = bookData?.title.toString(),
        style = Typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 19,
        modifier = Modifier.padding(bottom = 10.dp)
    )
    Row {
        Text(
            text = "Authors:",
            color = ColorSecondaryLight,
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = bookData?.authors.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp, start = 6.dp),
            textAlign = TextAlign.Start
        )
    }
    Row {
        Text(
            text = "Page Count:",
            color = ColorSecondaryLight,
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = bookData?.pageCount.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp, start = 6.dp),
            textAlign = TextAlign.Start
        )
    }
    Row {
        Text(
            text = "Categories:",
            color = ColorSecondaryLight,
            textDecoration = TextDecoration.Underline,
            maxLines = 3
        )
        Text(
            text = bookData?.categories.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp, start = 6.dp),
            textAlign = TextAlign.Start
        )
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Published:",
            color = ColorSecondaryLight,
            textDecoration = TextDecoration.Underline
        )
        Text(
            text = bookData?.publishedDate.toString(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp, start = 6.dp),
            textAlign = TextAlign.Start
        )
    }

    Spacer(modifier = Modifier.height(5.dp))

    val cleanDescription =
        HtmlCompat.fromHtml(bookData?.description.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
            .toString()
    val localDims = LocalContext.current.resources.displayMetrics
    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.09f))
            .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, ColorSecondaryLight)
    ) {
        LazyColumn(modifier = Modifier.padding(8.dp)) {
            item {
                Text(text = cleanDescription)
            }
        }
    }

    Row(
        modifier = Modifier.padding(top = 6.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RoundedButton(label = "Save") {
            val book = MBook(
                title = bookData?.title,
                authors = bookData?.authors.toString(),
                description = bookData?.description,
                categories = bookData?.categories.toString(),
                notes = "",
                photoUrl = bookData?.imageLinks?.thumbnail,
                publishedDate = bookData?.publishedDate,
                pageCount = bookData?.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            saveToFirebase(book, navController)
        }

        Spacer(modifier = Modifier.width(25.dp))
        RoundedButton(label = "Cancel") {
            navController.popBackStack()
        }
    }
}

fun saveToFirebase(book: MBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")
    if (book.toString().isNotEmpty()) {
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful)
                            navController.popBackStack()
                    }
                    .addOnFailureListener {
                        Logger.log(title = "saveToFirebase", message = "Error updating doc")
                    }
            }
            .addOnFailureListener {
                Logger.log(title = "saveToFirebase Failure", message = it.message.toString())

            }
    }
}
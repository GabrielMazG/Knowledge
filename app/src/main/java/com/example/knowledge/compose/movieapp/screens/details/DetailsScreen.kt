@file:OptIn(ExperimentalAnimationApi::class)

package com.example.knowledge.compose.movieapp.screens.details

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.knowledge.compose.movieapp.model.Movie
import com.example.knowledge.compose.movieapp.model.getMovies
import com.example.knowledge.compose.movieapp.widgets.MovieRow
import com.example.knowledge.compose.theme.ColorPrimaryDark
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.Typography

@Composable
fun DetailsScreen(navController: NavController, movieId: String?) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        val newMovieList = getMovies().filter { movie -> movie.id == movieId }
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = ColorSecondaryLight,
                    elevation = 5.dp
                ) {
                    Row(horizontalArrangement = Arrangement.Start) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back arrow",
                            modifier = Modifier.clickable {
                                navController.popBackStack()
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(100.dp))

                    Text(
                        text = "Movie",
                        color = ColorPrimaryDark
                    )
                }
            },
        ) {
            Text(
                text = movieId.toString(),
                style = Typography.h5
            )

            Surface(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(it)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    MovieRow(movie = newMovieList.first())
                    Divider()
                    Text(
                        text = newMovieList.first().title,
                        style = Typography.h5
                    )
                    HorizontalScrollableImageView(newMovieList)
                }
            }
        }
    }
}

@Composable
private fun HorizontalScrollableImageView(newMovieList: List<Movie>) {
    LazyRow {
        items(newMovieList.first().images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = 5.dp
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(
                            LocalContext.current
                        )
                            .crossfade(true)
                            .transformations(RoundedCornersTransformation(radius = 40f))
                            .data(image).build()
                    ), contentDescription = "Movie Poster"
                )
            }
        }
    }
}
package com.example.knowledge.compose.movieapp.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.RoundedCornersTransformation
import com.example.knowledge.compose.movieapp.model.Movie
import com.example.knowledge.compose.movieapp.model.getMovies
import com.example.knowledge.compose.theme.ColorSecondary
import com.example.knowledge.compose.theme.ColorSecondaryLight
import com.example.knowledge.compose.theme.Typography

@ExperimentalAnimationApi
@Preview
@Composable
fun MovieRow(movie: Movie = getMovies().first(), onItemClick: (String) -> Unit = {}) {
    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable {
                onItemClick(movie.id)
            }, shape = RoundedCornerShape(corner = CornerSize(16.dp)), elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Surface(
                modifier = Modifier
                    .padding(12.dp)
                    .size(100.dp),
                shape = RectangleShape,
                elevation = 4.dp
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .crossfade(true)
                            .transformations(RoundedCornersTransformation(radius = 40f))
                            .data(movie.images.first()).build()
                    ), contentDescription = "Movie image"
                )
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = movie.title, style = Typography.h6
                )
                Text(
                    text = "Director: ${movie.director}", style = Typography.caption
                )
                Text(
                    text = "Released: ${movie.year}", style = Typography.caption
                )
                AnimatedVisibility(visible = expanded) {
                    Column {
                        Text(buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    color = ColorSecondary,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Plot: ")
                            }
                            withStyle(
                                style = SpanStyle(
                                    color = ColorSecondary, fontSize = 13.sp
                                )
                            ) {
                                append(movie.plot)
                            }
                        }, modifier = Modifier.padding(6.dp))

                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 3.dp
                            ),
                            color = ColorSecondaryLight,
                            thickness = 0.2.dp
                        )
                        Text(text = "Director: ${movie.director}", style = Typography.caption)
                        Text(text = "Actors: ${movie.actors}", style = Typography.caption)
                        Text(text = "Rating: ${movie.rating}", style = Typography.caption)

                    }
                }
                Divider(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 12.dp
                    ),
                    color = ColorSecondaryLight,
                    thickness = 0.5.dp
                )
                Icon(
                    imageVector = if (expanded)
                        Icons.Filled.KeyboardArrowUp
                    else
                        Icons.Filled.KeyboardArrowDown,
                    contentDescription = "Down Arrow",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(30.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    tint = ColorSecondary
                )
            }
        }
    }
}
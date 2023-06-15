@file:OptIn(ExperimentalAnimationApi::class)

package com.example.composemodule.movieapp.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.composemodule.movieapp.model.Movie
import com.example.composemodule.movieapp.model.getMovies
import com.example.composemodule.movieapp.navigation.MovieScreens
import com.example.composemodule.movieapp.widgets.MovieRow

import com.example.composemodule.theme.ColorPrimaryDark
import com.example.composemodule.theme.ColorSecondaryLight

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = ColorSecondaryLight, elevation = 5.dp) {
                Text(
                    text = "Movie",
                    color = ColorPrimaryDark
                )
            }
        },
    ) {
        MainContent(navController = navController)
        it
    }
}

@Composable
fun MainContent(
    navController: NavController,
    movieList: List<Movie> = getMovies()
) {
    Column(modifier = Modifier.padding(8.dp)) {
        LazyColumn() {
            items(items = movieList) {
                MovieRow(movie = it) { movie ->
                    navController.navigate(route = MovieScreens.DetailsScreen.name + "/$movie")
                }
            }
        }
    }
}
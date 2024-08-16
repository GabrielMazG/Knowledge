package com.example.knowledge.compose.jetweatherforecast.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.knowledge.compose.Logger
import com.example.knowledge.compose.jetweatherforecast.model.Favorite
import com.example.knowledge.compose.jetweatherforecast.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {

    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect { listOfFavs ->
                    if (listOfFavs.isNullOrEmpty()) {
                        Logger.log(
                            title = "Get favs",
                            message = "Empty favs"
                        )
                    } else {
                        _favList.value = listOfFavs
                        Logger.log(
                            title = "Get favs",
                            message = "${favList.value}"
                        )
                    }
                }
        }
    }

    fun insertFavorite(favorite: Favorite) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavorite(favorite)
        }

    fun updateFavorite(favorite: Favorite) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateFavorite(favorite)
        }

    fun deleteFavorite(favorite: Favorite) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteFavorite(favorite)
        }

    fun deleteAllFavorites() =
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllFavorites()
        }
}
package com.nadhifhayazee.moviedb.screen.favorite_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.moviedb.commons.model.Resource
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.data.repository.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<Resource<List<UIMovie>>>(Resource.Initial())
    val favoriteMovies get() = _favoriteMovies.asStateFlow()

    init {
        getFavoriteMovies()
    }
    fun getFavoriteMovies() {
        viewModelScope.launch {
            movieRepository.getLocalMovies().collectLatest {
                _favoriteMovies.value = it
            }
        }
    }
}
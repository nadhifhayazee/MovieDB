package com.nadhifhayazee.moviedb.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.data.repository.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _moviesState = MutableStateFlow<PagingData<UIMovie>>(PagingData.empty())
    val moviesState get() = _moviesState.asStateFlow()

    init {
        getMovies()
    }


    fun getMovies() {
        viewModelScope.launch {
            movieRepository.getMovies()
                .cachedIn(this)
                .collectLatest {
                _moviesState.value = it
            }
        }
    }
}
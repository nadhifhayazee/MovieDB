package com.nadhifhayazee.moviedb.screen.search_movie

import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nadhifhayazee.moviedb.commons.model.Resource
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.data.repository.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchMovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    private val _moviesState = MutableStateFlow<PagingData<UIMovie>>(PagingData.empty())
    val moviesState get() = _moviesState.asStateFlow()

    init {
        viewModelScope.launch {
            searchQuery.debounce(300).collectLatest { query ->
                if (query.length > 3) searchMovies(query)
                else if (query.isEmpty()) searchMovies("")
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun searchMovies(query: String?) {
        viewModelScope.launch {
            repository.searchMovie(query)
                .debounce(1500)
                .cachedIn(this)
                .collectLatest {
                    _moviesState.value = it
                }
        }
    }
}
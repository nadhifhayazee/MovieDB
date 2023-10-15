package com.nadhifhayazee.moviedb.screen.detail_movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadhifhayazee.moviedb.commons.model.Resource
import com.nadhifhayazee.moviedb.commons.model.UIMovieDetail
import com.nadhifhayazee.moviedb.data.repository.movie.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movieState = MutableStateFlow<Resource<UIMovieDetail?>>(Resource.Initial())
    val movieState get() = _movieState.asStateFlow()

    private val _movieIsFavorite = MutableStateFlow<Boolean>(false)
    val movieIsFavorite get() = _movieIsFavorite.asStateFlow()

    fun getMovieDetail(id: Int) {
        viewModelScope.launch {
            movieRepository.getMovieById(id).collectLatest {
                _movieState.value = it
            }
        }
    }

    fun getMovieFavoriteState(id: Int) {
        viewModelScope.launch {
            viewModelScope.launch {
                movieRepository.movieIsExistInLocal(id).collectLatest {
                    _movieIsFavorite.value = it
                }
            }
        }
    }

    fun addMovieToFavorite(movie: UIMovieDetail?){
        viewModelScope.launch {
            if (movie != null) {
                if (_movieIsFavorite.value)
                    movie.id?.let { movieRepository.deleteLocalMovie(it).collectLatest {  } }
                else movieRepository.insertMovieToLocal(movie).collectLatest {  }
            }
        }
    }

}


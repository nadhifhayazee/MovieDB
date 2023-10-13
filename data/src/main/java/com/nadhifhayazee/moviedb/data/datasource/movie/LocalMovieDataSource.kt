package com.nadhifhayazee.moviedb.data.datasource.movie

import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.commons.model.UIMovieDetail
import com.nadhifhayazee.moviedb.data.room.dao.MovieDao
import com.nadhifhayazee.moviedb.data.room.entity.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(
    private val movieDao: MovieDao
) {

    fun getMovies(): Flow<List<UIMovie>> {
        return channelFlow {
            movieDao.getMovies().collectLatest { movies ->
                val mappingMovies = movies.map {
                    it.toUIMovie()
                }
                send(mappingMovies)
            }
        }
    }

    suspend fun getMovieById(id: Int): UIMovie? {
        return movieDao.getMovieById(id)?.toUIMovie()
    }

    suspend fun insertMovie(movie: UIMovieDetail) {
        movieDao.insertMovie(movie.toEntity())
    }

    suspend fun deleteMovie(id: Int) {
        movieDao.getMovieById(id)?.let {
            movieDao.deleteMovie(it)
        }
    }
}
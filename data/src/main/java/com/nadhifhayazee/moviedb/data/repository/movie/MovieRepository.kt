package com.nadhifhayazee.moviedb.data.repository.movie

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nadhifhayazee.moviedb.commons.model.Resource
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.commons.model.UIMovieDetail
import com.nadhifhayazee.moviedb.data.datasource.movie.LocalMovieDataSource
import com.nadhifhayazee.moviedb.data.datasource.movie.MoviePagingDataSource
import com.nadhifhayazee.moviedb.data.datasource.movie.RemoteMovieDataSource
import com.nadhifhayazee.moviedb.data.datasource.movie.SearchMoviePagingDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val remoteMovieDataSource: RemoteMovieDataSource,
    private val localMovieDataSource: LocalMovieDataSource
) {

    private val maxPage = 15
    fun getMovies(): Flow<PagingData<UIMovie>> {
        return Pager(
            config = PagingConfig(pageSize = maxPage),
            pagingSourceFactory = {
                MoviePagingDataSource(remoteMovieDataSource)
            }
        ).flow
    }

    fun searchMovie(query: String?): Flow<PagingData<UIMovie>> {
        return Pager(
            config = PagingConfig(pageSize = maxPage),
            pagingSourceFactory = {
                SearchMoviePagingDataSource(remoteMovieDataSource, query)
            }
        ).flow
    }

    suspend fun getMovieById(movieId: Int): Flow<Resource<UIMovieDetail?>> {
        return flow {
            try {
                emit(Resource.Loading())
                val movie = remoteMovieDataSource.getMovieById(movieId)
                emit(Resource.Success(movie))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }

    fun getLocalMovies(): Flow<Resource<List<UIMovie>>> {
        return channelFlow {
            try {
                send(Resource.Loading())
                localMovieDataSource.getMovies().collectLatest {
                    send(Resource.Success(it))
                }
            } catch (e: Exception) {
                send(Resource.Error(e))
            }
        }
    }

    fun insertMovieToLocal(movie: UIMovieDetail): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading())
                localMovieDataSource.insertMovie(movie)
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }

    fun movieIsExistInLocal(id: Int): Flow<Boolean> {
        return channelFlow {
            try {
                localMovieDataSource.getMovieById(id).collectLatest { movie ->
                    send(movie != null)
                }
            } catch (e: Exception) {
                send(false)
            }
        }
    }

    fun deleteLocalMovie(id: Int): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading())
                localMovieDataSource.deleteMovie(id)
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}
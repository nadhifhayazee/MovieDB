package com.nadhifhayazee.moviedb.data.datasource.movie

import com.nadhifhayazee.moviedb.commons.model.UIMovieDetail
import com.nadhifhayazee.moviedb.data.BuildConfig
import com.nadhifhayazee.moviedb.data.retrofit.extention.validateResponse
import com.nadhifhayazee.moviedb.data.retrofit.model.BasePagingResponse
import com.nadhifhayazee.moviedb.data.retrofit.model.Movie
import com.nadhifhayazee.moviedb.data.retrofit.model.MovieDetail
import com.nadhifhayazee.moviedb.data.retrofit.service.MovieService
import javax.inject.Inject

class RemoteMovieDataSource @Inject constructor(
    private val movieService: MovieService
) {

    suspend fun getMovies(page: Int?): BasePagingResponse<List<Movie>>? {

        var movies: BasePagingResponse<List<Movie>>? = null

        movieService.getPopularMovies(BuildConfig.API_KEY, page).validateResponse {
            movies = it
        }

        return movies
    }

    suspend fun searchMovie(query: String?, page: Int?): BasePagingResponse<List<Movie>>? {
        var movies: BasePagingResponse<List<Movie>>? = null

        movieService.searchMovie(BuildConfig.API_KEY, query, page).validateResponse {
            movies = it
        }

        return movies
    }

    suspend fun getMovieById(movieId: Int): UIMovieDetail? {
        var movie: UIMovieDetail? = null

        movieService.getMovieById(movieId, BuildConfig.API_KEY).validateResponse {
            movie = it.toUIMovieDetail()
        }

        return movie
    }

}
package com.nadhifhayazee.moviedb.data.retrofit.service

import com.nadhifhayazee.moviedb.data.retrofit.model.BasePagingResponse
import com.nadhifhayazee.moviedb.data.retrofit.model.Movie
import com.nadhifhayazee.moviedb.data.retrofit.model.MovieDetail
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String?,
        @Query("page") page: Int?,
    ): Response<BasePagingResponse<List<Movie>>>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String?,
        @Query("query") query: String?,
        @Query("page") page: Int?,
    ): Response<BasePagingResponse<List<Movie>>>


    @GET("movie/{movieId}")
    suspend fun getMovieById(
        @Query("api_key") apiKey: String?,
        @Path("movieId") movieId: Int
    ): Response<MovieDetail>

}
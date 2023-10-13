package com.nadhifhayazee.moviedb.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nadhifhayazee.moviedb.data.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * from favorite_movie")
    fun getMovies(): Flow<List<MovieEntity>>

    @Query("SELECT * from favorite_movie where id=:id")
    suspend fun getMovieById(id: Int): MovieEntity?

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Delete
    suspend fun deleteMovie(movieEntity: MovieEntity)


}
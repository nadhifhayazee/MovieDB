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
    fun getMovieById(id: Int): Flow<MovieEntity?>

    @Insert
    suspend fun insertMovie(movieEntity: MovieEntity)

    @Query("DELETE from favorite_movie where id=:id")
    suspend fun deleteMovie(id: Int)


}
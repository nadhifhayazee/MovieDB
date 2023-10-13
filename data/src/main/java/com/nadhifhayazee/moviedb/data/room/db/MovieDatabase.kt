package com.nadhifhayazee.moviedb.data.room.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nadhifhayazee.moviedb.data.room.dao.MovieDao
import com.nadhifhayazee.moviedb.data.room.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        const val DB_NAME = "movie_db"
    }
}
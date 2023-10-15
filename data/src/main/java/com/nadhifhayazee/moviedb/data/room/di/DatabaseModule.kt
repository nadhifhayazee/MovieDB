package com.nadhifhayazee.moviedb.data.room.di

import android.content.Context
import androidx.room.Room
import com.nadhifhayazee.moviedb.data.room.dao.MovieDao
import com.nadhifhayazee.moviedb.data.room.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(
        movieDb: MovieDatabase
    ): MovieDao {
        return movieDb.movieDao()
    }
}
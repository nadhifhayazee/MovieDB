package com.nadhifhayazee.moviedb.data.retrofit.di

import com.nadhifhayazee.moviedb.data.BuildConfig
import com.nadhifhayazee.moviedb.data.retrofit.service.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ServiceModule {

    @Provides
    fun provideMovieService(
        retrofitBuilder: Retrofit.Builder,
        okHttpClient: OkHttpClient.Builder,
    ): MovieService {
        return retrofitBuilder.client(okHttpClient.build()).baseUrl(BuildConfig.BASE_URL).build()
            .create(MovieService::class.java)
    }
}
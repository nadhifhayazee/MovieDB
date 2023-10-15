package com.nadhifhayazee.moviedb.data.retrofit.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nadhifhayazee.moviedb.commons.constant.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofitBuilder(
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit.Builder = Retrofit.Builder()
        .addConverterFactory(gsonConverterFactory)

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideOkHttpClientBuilder(
        @ApplicationContext context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: Interceptor,
    ): OkHttpClient.Builder = OkHttpClient().newBuilder()
        .addInterceptor(ChuckerInterceptor(context))
        .addInterceptor(headerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .readTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .writeTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .connectTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)
        .callTimeout(Constant.DEFAULT_TIMEOUT_IN_SECOND, TimeUnit.SECONDS)

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): Interceptor = Interceptor { chain ->
        val request: Request = chain.request().newBuilder()
            .addHeader(Constant.HEADER_ACCEPT, Constant.HEADER_APP_JSON)
            .addHeader(Constant.HEADER_CONTENT_TYPE, Constant.HEADER_APP_JSON)
            .build()
        chain.proceed(request)
    }

}
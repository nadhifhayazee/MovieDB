package com.nadhifhayazee.moviedb.commons.model

sealed class Resource<T> {
    class Initial<T>: Resource<T>()
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val throwable: Throwable) : Resource<T>()
}

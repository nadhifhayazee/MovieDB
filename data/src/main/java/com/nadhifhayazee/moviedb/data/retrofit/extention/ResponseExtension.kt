package com.nadhifhayazee.moviedb.data.retrofit.extention

import com.google.gson.Gson
import com.nadhifhayazee.moviedb.data.retrofit.model.ErrorResponse
import retrofit2.Response


suspend fun <T> Response<T>.validateResponse(
    onSuccess: suspend (data: T) -> Unit
) {

    if (this.isSuccessful) {
        val body = this.body()
        body?.let { onSuccess(it) }
    } else {
        val errorMessage = this.getErrorResponse()?.status_message
        throw Exception(errorMessage)
    }
}

suspend fun <T> Response<T>.getErrorResponse(): ErrorResponse? {
    return Gson().fromJson(
        this.errorBody()?.string(),
        ErrorResponse::class.java
    )

}
package com.nadhifhayazee.moviedb.data.retrofit.model

data class ErrorResponse(
    val success: Boolean?,
    val status_code: Int?,
    val status_message: String?
)

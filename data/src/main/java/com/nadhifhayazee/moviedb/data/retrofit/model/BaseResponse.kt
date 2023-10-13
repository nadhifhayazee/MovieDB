package com.nadhifhayazee.moviedb.data.retrofit.model

data class BasePagingResponse<T>(
    val page: Int?,
    val results: T,
    val total_pages: Int?,
    val total_results: Int?
)

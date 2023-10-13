package com.nadhifhayazee.moviedb.commons.model

data class UIMovieDetail(
    val id: Int?,
    val title: String?,
    val posterUrl: String?,
    val overview: String?,
    val genres: List<UIGenre>?,
    val voteAverage: Double?
)

data class UIGenre(
    val id: Int?,
    val name: String?
)

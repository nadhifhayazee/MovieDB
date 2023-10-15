package com.nadhifhayazee.moviedb.commons.model

import java.text.DecimalFormat

data class UIMovieDetail(
    val id: Int?,
    val title: String?,
    val posterUrl: String?,
    val overview: String?,
    val genres: List<UIGenre>?,
    val voteAverage: Double?,
    val backdropUrl: String?
) {
    fun getStringGenres(): String {
        return this.genres?.map { it.name }?.joinToString(separator = ", ") ?: ""
    }

    fun getStringRating(): String {
        return DecimalFormat("#.#").format(this.voteAverage)
    }
}


data class UIGenre(
    val id: Int?,
    val name: String?
)

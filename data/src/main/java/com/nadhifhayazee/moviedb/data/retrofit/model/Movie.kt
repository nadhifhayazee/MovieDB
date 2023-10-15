package com.nadhifhayazee.moviedb.data.retrofit.model

import com.nadhifhayazee.moviedb.commons.model.UIGenre
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.commons.model.UIMovieDetail
import com.nadhifhayazee.moviedb.data.BuildConfig

data class Movie(
    var id: Int?,
    val title: String?,
    var vote_average: Double?,
    var poster_path: String? = null,
) {
    fun toUIMovie(): UIMovie {
        return UIMovie(
            id, title, BuildConfig.IMAGE_URL + poster_path
        )
    }
}

data class MovieDetail(
    var id: Int?,
    val title: String?,
    val vote_average: Double?,
    val poster_path: String?,
    val backdrop_path: String?,
    val overview: String?,
    val release_date: String?,
    val runtime: Int?,
    val genres: List<Genre>?
) {
    fun toUIMovieDetail(): UIMovieDetail {
        return UIMovieDetail(
            id, title, BuildConfig.IMAGE_URL + poster_path, overview, genres.mapToUIGenre(), vote_average, BuildConfig.BACKDROP_URL + backdrop_path
        )
    }
}

data class Genre(
    val id: Int?,
    val name: String?
) {
    fun toUIGenre(): UIGenre {
        return UIGenre(
            id, name
        )
    }
}

fun List<Movie>?.mapToUIMovie(): List<UIMovie>? {
    return this?.map { it.toUIMovie() }
}

fun List<Genre>?.mapToUIGenre(): List<UIGenre>? {
    return this?.map { it.toUIGenre() }
}
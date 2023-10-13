package com.nadhifhayazee.moviedb.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nadhifhayazee.moviedb.commons.constant.Constant
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.commons.model.UIMovieDetail

@Entity(tableName = Constant.TABLE_FAVORITE_MOVIE)
data class MovieEntity(
    @PrimaryKey
    val id: Int? = 0,
    @ColumnInfo(name = "title")
    val title: String?,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double?,
    @ColumnInfo(name = "poster_path")
    val posterPath: String?,
) {
    fun toUIMovie(): UIMovie {
        return UIMovie(
            id = this.id,
            title = this.title,
            posterUrl = this.posterPath
        )
    }
}

fun UIMovieDetail.toEntity(): MovieEntity{
    return MovieEntity(
        id = this.id,
        title = this.title,
        voteAverage = this.voteAverage,
        posterPath = this.posterUrl
    )
}

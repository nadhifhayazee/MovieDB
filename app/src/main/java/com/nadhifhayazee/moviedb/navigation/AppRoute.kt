package com.nadhifhayazee.moviedb.navigation

sealed class AppRoute(val route: String) {
    object Home : AppRoute("home")
    object MovieDetail : AppRoute("movie/{movieId}"){
        fun createRoute(movieId: Int) = "movie/$movieId"
    }

    object Favorite : AppRoute("movie/favorite")

    object SearchMovie : AppRoute("movie/search")
}
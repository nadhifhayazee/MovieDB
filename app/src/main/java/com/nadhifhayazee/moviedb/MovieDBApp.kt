package com.nadhifhayazee.moviedb

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nadhifhayazee.moviedb.commons.constant.Constant
import com.nadhifhayazee.moviedb.navigation.AppRoute
import com.nadhifhayazee.moviedb.screen.detail_movie.DetailMovieScreen
import com.nadhifhayazee.moviedb.screen.favorite_movie.FavoriteMovieScreen
import com.nadhifhayazee.moviedb.screen.home.HomeScreen
import com.nadhifhayazee.moviedb.screen.search_movie.SearchMovieScreen
import com.nadhifhayazee.moviedb.ui.theme.MovieDBTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDBApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primary
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(paddingValues)
        ) {


            composable(AppRoute.Home.route) {
                HomeScreen(navHostController = navController)
            }

            composable(
                route = AppRoute.MovieDetail.route,
                arguments = listOf(navArgument(Constant.MOVIE_ID_KEY) { type = NavType.IntType })
            ) {
                val id = it.arguments?.getInt(Constant.MOVIE_ID_KEY) ?: -1
                DetailMovieScreen(navHostController = navController, movieId = id)
            }

            composable(
                route = AppRoute.SearchMovie.route
            ){
                SearchMovieScreen(navHostController = navController)
            }

            composable(
                route = AppRoute.Favorite.route
            ) {
                FavoriteMovieScreen(navHostController = navController)
            }

        }
    }

}

@Preview
@Composable
fun MovieDBAppPreview() {
    MovieDBTheme {
        MovieDBApp()
    }
}
package com.nadhifhayazee.moviedb.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.nadhifhayazee.moviedb.R
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.component.ErrorPagingMessage
import com.nadhifhayazee.moviedb.component.MovieListItem
import com.nadhifhayazee.moviedb.component.NotFoundMovies
import com.nadhifhayazee.moviedb.component.TopBar
import com.nadhifhayazee.moviedb.navigation.AppRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val moviesPaging = homeViewModel.moviesState.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopBar(onSearchClicked = {
                navHostController.navigate(AppRoute.SearchMovie.route)
            }) {
                navHostController.navigate(AppRoute.Favorite.route)
            }
        }
    ) { paddingValues ->


        HomeScreenContent(
            paddingValues,
            moviesPaging = moviesPaging,
            onItemSelected = { movie ->
                movie.id?.let {
                    navHostController.navigate(AppRoute.MovieDetail.createRoute(it))
                }
            })

    }


}

@Composable
fun HomeScreenContent(
    paddingValues: PaddingValues,
    moviesPaging: LazyPagingItems<UIMovie>,
    onItemSelected: (UIMovie) -> Unit
) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        moviesPaging.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(30.dp),

                        color = MaterialTheme.colorScheme.secondary
                    )

                }

                loadState.refresh is LoadState.Error -> {
                    NotFoundMovies(stringMessageId = R.string.not_found_movie_message) {
                        retry()
                    }
                }


            }

        }

        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Adaptive(minSize = 150.dp)
        ) {
            items(moviesPaging.itemCount) { index ->
                MovieListItem(
                    movie = moviesPaging[index]
                ) { selectedMovie ->
                    onItemSelected(selectedMovie)
                }
            }

            moviesPaging.apply {
                when {
                    loadState.append is LoadState.Error -> {
                        item {
                            val error = loadState.append as LoadState.Error
                            ErrorPagingMessage(
                                errorMessage = error.error.message ?: stringResource(
                                    id = R.string.default_error_get_movies
                                )
                            ) {
                                retry()
                            }
                        }
                    }
                }
            }
        }
    }


}
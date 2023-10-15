package com.nadhifhayazee.moviedb.screen.favorite_movie

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.nadhifhayazee.moviedb.R
import com.nadhifhayazee.moviedb.commons.exception.NoDataException
import com.nadhifhayazee.moviedb.commons.model.Resource
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.component.MovieListItem
import com.nadhifhayazee.moviedb.navigation.AppRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteMovieScreen(
    navHostController: NavHostController,
    viewModel: FavoriteMovieViewModel = hiltViewModel()
) {

    val favorites by viewModel.favoriteMovies.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.favorite_movies),
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.back_content_description),
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
        }
    ) { paddingValues ->
        FavoriteMovieContent(paddingValues = paddingValues, favorite = favorites) { movie ->
            movie.id?.let {
                navHostController.navigate(AppRoute.MovieDetail.createRoute(it))
            }
        }
    }

}

@Composable
fun FavoriteMovieContent(
    paddingValues: PaddingValues,
    favorite: Resource<List<UIMovie>>,
    onItemSelected: (UIMovie) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (favorite) {
            is Resource.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(30.dp),

                    color = MaterialTheme.colorScheme.secondary
                )
            }

            is Resource.Success -> {


                LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Adaptive(minSize = 150.dp)) {
                    items(favorite.data) { movie ->
                        MovieListItem(
                            movie = movie
                        ) { selectedMovie ->
                            onItemSelected(selectedMovie)
                        }
                    }
                }


            }

            is Resource.Error -> {
                if (favorite.throwable is NoDataException) {
                    Text(
                        modifier = Modifier,
                        text = stringResource(id = R.string.not_found_movie_message),
                        color = Color.Gray,
                    )
                } else {
                    Toast.makeText(
                        LocalContext.current, favorite.throwable.message ?: stringResource(
                            id = R.string.default_error_get_movies
                        ), Toast.LENGTH_SHORT
                    ).show()
                }
            }

            else -> Unit
        }

    }
}
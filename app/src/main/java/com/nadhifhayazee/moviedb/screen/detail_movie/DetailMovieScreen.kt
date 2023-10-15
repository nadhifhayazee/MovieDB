package com.nadhifhayazee.moviedb.screen.detail_movie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nadhifhayazee.moviedb.R
import com.nadhifhayazee.moviedb.commons.model.Resource
import com.nadhifhayazee.moviedb.commons.model.UIMovieDetail

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailMovieScreen(
    navHostController: NavHostController,
    movieId: Int?,
    viewModel: DetailMovieViewModel = hiltViewModel()
) {

    val movieState by viewModel.movieState.collectAsState()
    val movieFavoriteState by viewModel.movieIsFavorite.collectAsState()
    LaunchedEffect(true) {
        movieId?.let {
            viewModel.getMovieFavoriteState(it)
            viewModel.getMovieDetail(it)
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (movieState) {
                        is Resource.Success -> Text(
                            text = (movieState as Resource.Success<UIMovieDetail?>).data?.title
                                ?: "",
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        else -> Unit
                    }
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

        DetailMovieContent(
            paddingValues = paddingValues,
            movieState = movieState,
            movieFavoriteState
        ) {
            viewModel.addMovieToFavorite(it)
        }


    }
}

@Composable
fun DetailMovieContent(
    paddingValues: PaddingValues,
    movieState: Resource<UIMovieDetail?>,
    movieFavorite: Boolean,
    onFavoriteClicked: (UIMovieDetail?) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {


        when (movieState) {
            is Resource.Loading -> {

            }

            is Resource.Success -> {
                MovieDetailLoaded(movie = movieState.data, movieFavorite) {
                    onFavoriteClicked(movieState.data)
                }
            }

            else -> Unit
        }
    }
}

@Composable
fun MovieDetailLoaded(
    movie: UIMovieDetail?,
    movieFavorite: Boolean,
    onFavoriteClicked: (UIMovieDetail?) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier.aspectRatio(16 / 9f),
            model = movie?.backdropUrl,
            contentDescription = stringResource(id = R.string.movie_poster_content_description)
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
            text = movie?.title ?: "",
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        )

        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = movie?.getStringGenres() ?: "",
            fontSize = 12.sp,
            color = Color.LightGray
        )

        Text(
            modifier = Modifier.padding(16.dp),
            text = movie?.overview ?: "",
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        ButtonAddToFavorite(movie = movie, movieFavorite) {
            onFavoriteClicked(movie)
        }
    }
}

@Composable
fun ButtonAddToFavorite(
    movie: UIMovieDetail?,
    movieFavorite: Boolean,
    onFavoriteClicked: (UIMovieDetail?) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
    ) {

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "",
                Modifier
                    .size(30.dp)
                    .padding(bottom = 8.dp),
                tint = MaterialTheme.colorScheme.secondary
            )

            Text(text = "${movie?.getStringRating()} / 10", color = Color.White)
        }

        Button(
            modifier = Modifier
                .weight(3f)
                .padding(start = 8.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            onClick = {
                onFavoriteClicked.invoke(
                    movie
                )
            }) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (!movieFavorite) Icons.Default.FavoriteBorder else Icons.Default.Favorite,
                    contentDescription = "",
                    Modifier
                        .padding(horizontal = 8.dp)
                        .size(28.dp),
                    tint = Color.Black
                )
                Text(
                    text = if (!movieFavorite) "Add to Favorite" else "Remove from Favorite",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

        }

    }
}
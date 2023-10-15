package com.nadhifhayazee.moviedb.screen.search_movie

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.nadhifhayazee.moviedb.R
import com.nadhifhayazee.moviedb.component.SearchBar
import com.nadhifhayazee.moviedb.navigation.AppRoute
import com.nadhifhayazee.moviedb.screen.home.HomeScreenContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchMovieScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: SearchMovieViewModel = hiltViewModel()
) {
    val searchResultState = viewModel.moviesState.collectAsLazyPagingItems()
    val query by viewModel.searchQuery.collectAsState()


    Scaffold(
        topBar = {
            TopAppBar(title = {
                SearchBar(value = query, onValueChange = {
                    viewModel.onSearchQueryChange(it)
                })
            }, navigationIcon = {
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

        HomeScreenContent(
            paddingValues,
            moviesPaging = searchResultState,
            onItemSelected = { movie ->
                movie.id?.let {
                    navHostController.navigate(AppRoute.MovieDetail.createRoute(it))
                }
            })

    }

}
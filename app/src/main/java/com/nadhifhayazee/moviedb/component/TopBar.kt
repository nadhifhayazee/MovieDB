package com.nadhifhayazee.moviedb.component

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.nadhifhayazee.moviedb.R
import com.nadhifhayazee.moviedb.ui.theme.MovieDBTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    onSearchClicked: () -> Unit,
    onFavoriteClicked: () -> Unit,
) {

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        },
        actions = {
            IconButton(onClick = {
                onFavoriteClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.favorite_content_description),
                    tint = Color.White
                )
            }

            IconButton(onClick = {
                onSearchClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.favorite_content_description),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary )
    )
}

@Preview
@Composable
fun TopBarPreview() {
    MovieDBTheme {
        TopBar(onSearchClicked = { /*TODO*/ }) {

        }
    }
}
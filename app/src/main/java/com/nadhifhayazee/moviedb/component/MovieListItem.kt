package com.nadhifhayazee.moviedb.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.nadhifhayazee.moviedb.R
import com.nadhifhayazee.moviedb.commons.model.UIMovie
import com.nadhifhayazee.moviedb.data.BuildConfig

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListItem(
    movie: UIMovie?,
    onItemSelected: (UIMovie) -> Unit
) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        onClick = { movie?.let { onItemSelected(it) } }
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 230.dp),
                model = movie?.posterUrl,
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(30.dp).size(16.dp),
                        color = MaterialTheme.colorScheme.secondary
                    )
                },
                contentDescription = stringResource(id = R.string.movie_poster_content_description),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                                MaterialTheme.colorScheme.primary
                            )
                        )
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
                    text = movie?.title ?: "",
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}
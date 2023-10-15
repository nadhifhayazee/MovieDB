package com.nadhifhayazee.moviedb.component

import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadhifhayazee.moviedb.R

@Composable
fun ErrorPagingMessage(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRetry: () -> Unit
) {

    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(Alignment.CenterVertically)
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = errorMessage,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 12.sp,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )

            IconButton(onClick = onRetry) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(id = R.string.button_retry_content_description),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
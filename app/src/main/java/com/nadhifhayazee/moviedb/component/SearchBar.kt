package com.nadhifhayazee.moviedb.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadhifhayazee.moviedb.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {

    Row(
        modifier = modifier
            .padding(12.dp)
            .height(50.dp)
            .background(
                MaterialTheme.colorScheme.background, shape =
                RoundedCornerShape(20.dp)
            )
    ) {

        TextField(
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = { onValueChange(value) }),
            value = value,
            textStyle = TextStyle(fontSize = 14.sp),
            onValueChange = { newValue -> onValueChange(newValue) },
            placeholder = {
                Text(
                    textAlign = TextAlign.Left,
                    text = stringResource(id = R.string.search_movie_placeholder),
                    color = Color.Gray,
                )
            },

            )
    }
}
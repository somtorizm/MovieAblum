package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.unit.dp
import com.ramcosta.composedestinations.annotation.Destination


@Composable
@Destination
fun SearchFragment() {
    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(
            value = "wpw",
            onValueChange = {
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search")
            },
            maxLines = 1,
            singleLine = true
        )
    }
}

package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun RoundImage(
    image: Painter,
    modifier: Modifier = Modifier,

    ) {
    Image(
        painter = image, contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .padding(1.dp)
            .size(5.dp)
            .clip(CircleShape)
    )

}

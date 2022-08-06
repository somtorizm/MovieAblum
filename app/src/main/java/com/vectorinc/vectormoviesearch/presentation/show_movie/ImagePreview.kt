package com.vectorinc.vectormoviesearch.presentation.show_movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue

@Composable
fun ImagePreview(
    painter: Painter,
    modifier: Modifier,
    color : Color
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxWidth()

    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            color,
                        ),
                        startY = 300f

                    )
                )
        )
    }

}

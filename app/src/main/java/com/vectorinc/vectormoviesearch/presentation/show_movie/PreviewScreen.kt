package com.vectorinc.vectormoviesearch.presentation.show_movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue
import com.vectorinc.vectormoviesearch.util.PreviewItems

@Destination
@Composable
fun PreviewScreen(
     adult: Boolean,
    backdropPath: String,
    id: Int,
     language: String,
     titleOriginal: String,
    description: String,
    popularity: Double,
    imagePoster: String,
     dateReleased: String?,
     title: String,
     hasVideo: Boolean,
     voteAverage: Double,
      voteCount: Int,
) {
    Box(Modifier.fillMaxSize(0.5f)) {
        val movieUrl =
            rememberAsyncImagePainter(
                "https://image.tmdb.org/t/p/original" + imagePoster
            )
        ImagePreview(
            painter = movieUrl,
            modifier = Modifier.fillMaxWidth()
        )

    }
    Column(modifier = Modifier.fillMaxSize()) {

    }



}





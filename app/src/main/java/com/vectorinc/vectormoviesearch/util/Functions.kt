package com.vectorinc.vectormoviesearch.util

import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberAsyncImagePainter

class Functions {
    fun convertToPercentage(voteAverage: Double): Float {
        return voteAverage.toFloat() / 10
    }


}

package com.vectorinc.vectormoviesearch.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberImagePainter
import coil.request.videoFrameMillis
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun convertToPercentage(voteAverage: Double): Float {
    return voteAverage.toFloat() / 10
}

fun convertToDate(date: String): String {
    val date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return "${date.dayOfWeek}" + " " + "${date.month}" + " " + "${date.year}"
}


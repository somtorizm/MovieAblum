package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue

@Composable
fun RatingBarItem(
    voteRate : Double,
    number: Int,
    fontSize: TextUnit = 18.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth : Dp = 2.dp,
    aminDuration: Int = 1000,
    animDelay: Int = 0,
    modifier: Modifier
) {
    val voteRateDigit  = voteRate
    val percentage = voteRateDigit.toFloat() / 10
    var color  = color
    color = if ( voteRate>= 7.0 ){
        Color.Green
    }else Color.Yellow

    var animationPlayed by remember {
        mutableStateOf(false)
    }


    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = aminDuration,
            delayMillis = animDelay,

            )
    )
    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.size(radius * 2).background(DarkBlue, CircleShape)
    ) {
        Canvas(modifier = Modifier.size(radius * 2)){
            drawArc(
                color = color,
                -9f,
                360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Text(
            text = ((curPercentage.value * number).toInt()/10).toDouble() .toString(),
            fontSize = fontSize,
            fontWeight = FontWeight.Light
        )
    }

}
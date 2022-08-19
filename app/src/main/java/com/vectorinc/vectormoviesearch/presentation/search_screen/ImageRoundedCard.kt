package com.vectorinc.vectormoviesearch.presentation.search_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.presentation.destinations.PreviewScreenDestination

@Composable
fun ImageCardRounded(
    painter: Painter,
    modifier: Modifier = Modifier,
    movieTitle: String,
    voteRate: Double,
    result: Result? = null,
    movieId : Int,
    navigator: DestinationsNavigator

) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            if (result != null) {
                navigator.navigate(
                    PreviewScreenDestination(movieId)
                )
            }
        }) {
        Box() {
            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                elevation = 1.dp
            ) {
                Box(
                    modifier = Modifier
                        .height(170.dp)
                        .width(120.dp)
                ) {
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )

                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Text(
                    text = voteRate.toString(), fontSize = 9.sp,
                    fontWeight = FontWeight.SemiBold,

                    modifier = Modifier
                        .background(
                            color = Color.DarkGray,
                            RoundedCornerShape(20)
                        )
                        .padding(5.dp)
                )

            }
        }


        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (movieTitle == "null") "" else movieTitle,
            fontWeight = FontWeight.SemiBold,
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            maxLines = 1,
        )
    }


}
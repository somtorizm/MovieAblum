package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue

@Composable
fun ImageCardRow(
    voteRate: Double,
    painter: Painter,
    modifier: Modifier = Modifier,
    movieTitle: String,
    movieOriginalTitle: String,
    movieDescription: String
) {
    Row(modifier = modifier, Arrangement.SpaceAround) {
        Box(
            modifier = modifier
                .fillMaxWidth(0.5f)
                .padding(horizontal = 15.dp)
        ) {

            Card(
                modifier = modifier.fillMaxWidth(),
                shape = RoundedCornerShape(15.dp),
                elevation = 8.dp
            ) {
                Box(
                    modifier = Modifier
                        .height(200.dp)
                        .width(250.dp)
                ) {
                    Image(
                        modifier = Modifier.fillMaxWidth(),
                        painter = painter,
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        DarkBlue,
                                    ),
                                    startY = 300f

                                )
                            )
                    ) {

                    }
                }

            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                RatingBarItem(
                    voteRate = voteRate,
                    number = 100,
                    radius = 12.dp,
                    color = Color.Yellow,
                    fontSize = 10.sp,
                    modifier = Modifier
                )
            }


        }



        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(1.dp)
        ) {
            Text(text = movieTitle, fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = movieOriginalTitle,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = movieDescription,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 9

            )


        }

    }


}
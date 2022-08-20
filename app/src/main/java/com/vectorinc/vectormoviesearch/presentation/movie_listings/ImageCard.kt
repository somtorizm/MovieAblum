package com.vectorinc.vectormoviesearch.presentation.movie_listings

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.presentation.destinations.PreviewScreenDestination
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue

@Composable
fun ImageCard(
    painter: Painter,
    modifier: Modifier = Modifier,
    movieTitle: String,
    movieOriginalTitle : String,
    voteRate: Double,
    navigator: DestinationsNavigator,
    movieID : Int

    ) {
    Column(modifier = Modifier.fillMaxWidth().clickable {
        navigator.navigate(PreviewScreenDestination(movieID))
    }) {
       Box() {
           Card(
               modifier = modifier.fillMaxWidth(),
               shape = RoundedCornerShape(15.dp),
               elevation = 5.dp
           ) {
               Box(
                   modifier = Modifier
                       .height(160.dp)
                       .width(120.dp)
               ) {
                   Image(
                       painter = painter,
                       contentDescription = null,
                       modifier = Modifier.fillMaxWidth().align(Alignment.Center),
                       contentScale = ContentScale.Crop
                   )
                   Box(
                       modifier = Modifier
                           .fillMaxSize()
                           .background(
                               Brush.verticalGradient(
                                   listOf(
                                       Color.Transparent,
                                       Color.Transparent,
                                   ),
                                   startY = 200f

                               )
                           )
                   ) {

                   }
               }

           }
           Box(
               modifier = Modifier
                   .fillMaxWidth()
                   .height(160.dp),
               contentAlignment = Alignment.BottomEnd
           ) {
               RatingBarItem(
                   voteRate = voteRate,
                   number = 100,
                   radius = 10.dp,
                   color = Color.Yellow,
                   fontSize = 8.sp,
                   modifier = Modifier
               )
           }
       }


        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if(movieTitle == "null") "" else movieTitle,
            fontWeight = FontWeight.SemiBold,
            color = Color.LightGray,
            modifier = Modifier
                .width(120.dp),
            overflow = TextOverflow.Ellipsis,
            fontSize = 12.sp,
            maxLines = 2,

        )
    }


}
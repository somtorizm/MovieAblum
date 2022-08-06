package com.vectorinc.vectormoviesearch.presentation.show_movie

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.vectorinc.vectormoviesearch.presentation.movie_listings.RatingBarItem
import com.vectorinc.vectormoviesearch.presentation.search_screen.Loading
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue

@Destination
@Composable
fun PreviewScreen(
    movieID: Int,
    viewModel: PreviewViewModel = hiltViewModel()
) {
    val state = viewModel.state
    if (!state.isLoading) {
        Loading()
    } else {
        val movieUrlBackDrop =
            rememberAsyncImagePainter(
                "https://image.tmdb.org/t/p/original" + state.movies?.backdrop_path
            )
        val movieUrlImage =
            rememberAsyncImagePainter(
                "https://image.tmdb.org/t/p/original" + state.movies?.poster_path
            )

        Column(modifier = Modifier.fillMaxSize()) {
            Box(Modifier.fillMaxHeight(0.2f)) {

                ImagePreview(
                    painter = movieUrlBackDrop,
                    modifier = Modifier.fillMaxWidth(),
                )

            }
            Box(Modifier.fillMaxSize().offset(0.dp, (-50).dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 15.dp)
                ) {

                    Card(
                        modifier = Modifier.fillMaxWidth(),
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
                                painter = movieUrlImage,
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
                            voteRate = state.movies?.vote_average ?: 0.0,
                            number = 100,
                            radius = 12.dp,
                            color = Color.Yellow,
                            fontSize = 10.sp,
                            modifier = Modifier
                        )
                    }


                }

            }


        }

    }

}

@Composable
fun ActionListings() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Action",
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .background(Color.DarkGray, RoundedCornerShape(20))
                .padding(10.dp)

        )
        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "Fantasy",
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .background(Color.DarkGray, RoundedCornerShape(20))
                .padding(10.dp)

        )
        Spacer(modifier = Modifier.width(5.dp))

        Text(
            text = "Adventure",
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .background(Color.DarkGray, RoundedCornerShape(20))
                .padding(10.dp)

        )
    }
}








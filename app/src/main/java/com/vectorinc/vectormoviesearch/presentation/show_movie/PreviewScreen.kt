package com.vectorinc.vectormoviesearch.presentation.show_movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.vectorinc.vectormoviesearch.presentation.search_screen.Loading

@Destination
@Composable
fun PreviewScreen(
    movieID: Int,
    viewModel: PreviewViewModel = hiltViewModel()
) {
    val state = viewModel.state
    if (!state.isLoading){
        Loading()
    }else {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(Modifier.height(400.dp)) {
                val movieUrl =
                    rememberAsyncImagePainter(
                        "https://image.tmdb.org/t/p/original" + ""
                    )
                ImagePreview(
                    painter = movieUrl,
                    modifier = Modifier.fillMaxWidth(),
                )

            }
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier
                        .padding(0.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "IMDB",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier
                            .background(Color.Yellow, RoundedCornerShape(20))
                            .padding(10.dp)

                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Image(
                        painter = painterResource(id = com.vectorinc.vectormoviesearch.R.drawable.ic_baseline_star_24),
                        contentDescription = "Rating Star", modifier = Modifier.size(35.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "8.3",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 28.sp,

                        )
                }
                Text(
                    text = "",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))

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

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = "",
                    fontWeight = FontWeight.Light,
                    fontSize = 15.sp,
                    overflow = TextOverflow.Ellipsis,
                )
            }

        }

    }






}






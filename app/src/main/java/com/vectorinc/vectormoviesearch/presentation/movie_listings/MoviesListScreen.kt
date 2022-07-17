package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.vectorinc.vectormoviesearch.util.Functions


@Composable
@Destination(start = true)
fun ShowMovies(
    viewModel: MovieListingViewModel = hiltViewModel()
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original/"
    val convert = Functions()

    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )



    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(name = "Victor", modifier = Modifier.padding(10.dp))
        Spacer(modifier = Modifier.height(20.dp))
        if (state.movies != null) {
            val size = state.movies.result?.size ?: 0
            val randomItem = (0..(size-1)).random()
            val movieUrl =
                rememberAsyncImagePainter(
                    baseImageUrl + state.movies.result?.get(
                        randomItem
                    )?.imagePoster
                )
            val movieTitle = state.movies.result?.get(randomItem)?.title
            val movieOriginalTitle = state.movies.result?.get(randomItem)?.titleOriginal
            val voteRate = state.movies.result?.get(randomItem)?.voteAverage ?: 0.0

            val movieDescription = state.movies.result?.get(randomItem)?.description
            ImageCardRow(
                painter = movieUrl,
                modifier = Modifier,
                voteRate = voteRate,
                movieTitle = movieTitle.toString(),
                movieOriginalTitle = movieOriginalTitle.toString(),
                movieDescription = movieDescription.toString()
            )
        }


        Spacer(modifier = Modifier.height(5.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {

            Text(
                text = "Trending Movies",
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = Color.LightGray
            )
            Icon(
                painter = painterResource(id = com.vectorinc.vectormoviesearch.R.drawable.ic_baseline_arrow_forward_24),
                contentDescription = "Trending Movies"
            )

        }


        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(15) { i ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    val movieUrl =
                        rememberAsyncImagePainter(
                            baseImageUrl + state.movies?.result?.get(
                                i
                            )?.imagePoster
                        )
                    val movieTitle = state.movies?.result?.get(i)?.title
                    val voteRate = state.movies?.result?.get(i)?.voteAverage ?: 0.0

                    ImageCard(
                        painter = movieUrl,
                        movieTitle = movieTitle.toString(),
                        modifier = Modifier,
                        voteRate = voteRate
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))


    }

}













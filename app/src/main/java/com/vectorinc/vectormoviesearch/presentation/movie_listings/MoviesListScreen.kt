package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue
import com.vectorinc.vectormoviesearch.util.Functions


@Destination()
@Composable
fun ShowMovies(
    navigator: DestinationsNavigator,
    viewModel: MovieListingViewModel = hiltViewModel()
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original/"
    val convert = Functions()


    val state = viewModel.state
    val stateTrending = viewModel.stateTrending
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )




    Column() {
        TopBar(
            name = "Victor", modifier = Modifier
                .padding(10.dp)
                .weight(1f)
        )
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.onEvent(MoviesListingEvent.Refresh)
        }, Modifier.weight(8f)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(15.dp))
                if (state.movies != null) {


                    val size = state.movies.result?.size ?: 0
                    val randomItem = (0..(size - 1)).random()

                    val movieUrl =
                        rememberAsyncImagePainter(
                            baseImageUrl + state.movies.result?.get(
                                randomItem
                            )?.imagePoster
                        )
                    val movieTitle = state.movies.result?.get(randomItem)?.title
                    val movieOriginalTitle = state.movies.result?.get(randomItem)?.titleOriginal
                    val voteRate = state.movies.result?.get(randomItem)?.voteAverage ?: 0.0
                    val item = state.movies.result?.get(randomItem)

                    val movieDescription = state.movies.result?.get(randomItem)?.description
                    ImageCardRow(
                        painter = movieUrl,
                        modifier = Modifier,
                        voteRate = voteRate,
                        movieTitle = movieTitle.toString(),
                        movieOriginalTitle = movieOriginalTitle.toString(),
                        movieDescription = movieDescription.toString(),
                        navigator = navigator,
                        result = item
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))

                listItems(name = "Trending Movies")

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    items(stateTrending.movies?.result?.size ?: 5) { i ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp)
                        ) {
                            val movieUrl =
                                rememberAsyncImagePainter(
                                    baseImageUrl + stateTrending.movies?.result?.get(
                                        i
                                    )?.imagePoster
                                )
                            val movieTitle = stateTrending.movies?.result?.get(i)?.title
                            val movieOrginalTitle =
                                stateTrending.movies?.result?.get(i)?.titleOriginal

                            val voteRate = stateTrending.movies?.result?.get(i)?.voteAverage ?: 0.0

                            ImageCard(
                                painter = movieUrl,
                                movieTitle = movieTitle.toString(),
                                modifier = Modifier,
                                voteRate = voteRate,
                                movieOriginalTitle = movieOrginalTitle.toString()
                            )
                        }
                    }
                }
                listItems(name = "NowPlaying Movies")
                LazyRow(modifier = Modifier.fillMaxWidth()) {
                    items(state.movies?.result?.size ?: 2) { i ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            val movieUrl =
                                rememberAsyncImagePainter(
                                    baseImageUrl + state.movies?.result?.get(
                                        i
                                    )?.imagePoster
                                )
                            val movieTitle = state.movies?.result?.get(i)?.title
                            val voteRate = state.movies?.result?.get(i)?.voteAverage ?: 0.0
                            val movieOrginalTitle = state.movies?.result?.get(i)?.titleOriginal

                            ImageCard(
                                painter = movieUrl,
                                movieTitle = movieTitle.toString(),
                                modifier = Modifier,
                                voteRate = voteRate,
                                movieOriginalTitle = movieOrginalTitle.toString()
                            )
                        }
                    }
                }


            }
        }
        BottomNavigationBar(
            modifier = Modifier
                .weight(1f)
                .background(color = DarkBlue, shape = RoundedCornerShape(15.dp))
                .padding(10.dp)
        )


    }


}
















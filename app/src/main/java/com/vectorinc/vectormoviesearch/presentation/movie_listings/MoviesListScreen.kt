package com.vectorinc.vectormoviesearch.presentation.movie_listings

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.presentation.MovieAppState
import com.vectorinc.vectormoviesearch.presentation.OfflineDialog
import com.vectorinc.vectormoviesearch.presentation.destinations.SearchDestination
import com.vectorinc.vectormoviesearch.presentation.destinations.ShowMoviesDestination
import com.vectorinc.vectormoviesearch.presentation.rememberMoviesAppState
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue
import com.vectorinc.vectormoviesearch.ui.theme.Yellow800
import com.vectorinc.vectormoviesearch.util.verticalGradientScrim


@Destination()
@Composable
fun ShowMovies(
    navigator: DestinationsNavigator,
    appState: MovieAppState = rememberMoviesAppState(),
    viewModel: MovieListingViewModel = hiltViewModel()
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original/"


    val state = viewModel.state
    val stateTrending = viewModel.stateTrending
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )


    if (!appState.isOnline) {
        OfflineDialog {
              navigator.navigate(ShowMoviesDestination)
        }

    } else {


        Column(
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(
                    WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                )
                .verticalGradientScrim(
                    color = DarkBlue.copy(alpha = 0.50f),
                    startYPercentage = 1f,
                    endYPercentage = 0f
                )
        ) {
            val appBarColor = DarkBlue.copy(alpha = 0.90f)

            Spacer(
                Modifier
                    .background(appBarColor)
                    .fillMaxWidth()
                    .windowInsetsTopHeight(androidx.compose.foundation.layout.WindowInsets.statusBars)
            )

            TopAppBar(
                title = {
                    Row {
                        Image(
                            painter = painterResource(com.vectorinc.vectormoviesearch.R.drawable.logo),
                            contentDescription = stringResource(com.vectorinc.vectormoviesearch.R.string.app_name),
                            modifier = Modifier.fillMaxWidth(0.5f),
                            contentScale = ContentScale.Crop

                        )
                    }
                },

                backgroundColor = appBarColor,
                actions = {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        IconButton(
                            onClick = { navigator.navigate(SearchDestination) }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = stringResource(com.vectorinc.vectormoviesearch.R.string.search)
                            )
                        }
                    }
                },
                modifier = Modifier
            )

            SwipeRefresh(state = swipeRefreshState, onRefresh = {
                viewModel.onEvent(MoviesListingEvent.Refresh)
            }, Modifier.weight(9f)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(modifier = Modifier.height(15.dp))
                    if (state.movies != null) {

                        val imageUrl = state.movies.result?.get(0)?.imagePoster
                        val movieTitle = state.movies.result?.get(0)?.title
                        val movieOriginalTitle = state.movies.result?.get(0)?.titleOriginal
                        val voteRate = state.movies.result?.get(0)?.voteAverage ?: 0.0
                        val item = state.movies.result?.get(0)

                        val movieDescription = state.movies.result?.get(0)?.description
                        ImageCardRow(
                            imageurl = imageUrl.toString(),
                            modifier = Modifier,
                            voteRate = voteRate,
                            movieTitle = movieTitle.toString(),
                            movieOriginalTitle = movieOriginalTitle.toString(),
                            movieDescription = movieDescription.toString(),
                            navigator = navigator,
                            result = item,
                            movieId = item?.id ?: 0
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

                                val voteRate =
                                    stateTrending.movies?.result?.get(i)?.voteAverage ?: 0.0

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


        }
    }
}




















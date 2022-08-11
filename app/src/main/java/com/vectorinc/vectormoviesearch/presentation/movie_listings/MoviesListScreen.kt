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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.presentation.MovieAppState
import com.vectorinc.vectormoviesearch.presentation.OfflineDialog
import com.vectorinc.vectormoviesearch.presentation.destinations.SearchDestination
import com.vectorinc.vectormoviesearch.presentation.destinations.ShowMoviesDestination
import com.vectorinc.vectormoviesearch.presentation.rememberMoviesAppState
import com.vectorinc.vectormoviesearch.presentation.search_screen.Loading
import com.vectorinc.vectormoviesearch.presentation.search_screen.Search
import com.vectorinc.vectormoviesearch.presentation.show_movie.PreviewEvents
import com.vectorinc.vectormoviesearch.ui.theme.MinContrastOfPrimaryVsSurface
import com.vectorinc.vectormoviesearch.util.DynamicThemePrimaryColorsFromImage
import com.vectorinc.vectormoviesearch.util.contrastAgainst
import com.vectorinc.vectormoviesearch.util.rememberDominantColorState
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
        OfflineDialog(title = stringResource(id = R.string.connection_error_title),
            message = stringResource(
                id = R.string.connection_error_message
            ),
            onRetry = {
                navigator.navigate(ShowMoviesDestination)
            }, navigator = navigator, onBack = {
                //TODO
            })

    } else {
        if (!state.isLoading) {
            Loading()
        } else {
            val surfaceColor = MaterialTheme.colors.surface
            val dominantColorState = rememberDominantColorState { color ->
                // We want a color which has sufficient contrast against the surface color
                color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
            }

            DynamicThemePrimaryColorsFromImage(dominantColorState) {
                val baseImageUrl = "https://image.tmdb.org/t/p/original/"

                val selectedImageUrl = baseImageUrl + state.movies?.result?.get(0)?.imagePoster

                // When the selected image url changes, call updateColorsFromImageUrl() or reset()
                LaunchedEffect(selectedImageUrl) {
                    if (selectedImageUrl != null) {
                        dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
                        Log.d("Image", "$selectedImageUrl")
                    } else {
                        dominantColorState.reset()
                    }
                }
                val color = MaterialTheme.colors.primary.copy(alpha = 0.4f)
                Column(
                    Modifier
                        .fillMaxWidth()
                        .windowInsetsPadding(
                            WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                        )
                        .verticalGradientScrim(
                            color = color,
                            startYPercentage = 0.95f,

                            endYPercentage = 0.0f
                        )
                ) {

                    val appBarColor = Color.Red.copy(alpha = 0.0020f)

                    Spacer(
                        Modifier
                            .background(appBarColor)
                            .fillMaxWidth()
                            .windowInsetsTopHeight(androidx.compose.foundation.layout.WindowInsets.statusBars)
                    )

                    TopAppBar(
                        elevation = 0.dp,
                        title = {
                            Image(
                                painter = painterResource(id = R.drawable.ic_creaview),
                                contentDescription = stringResource(R.string.search),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.offset((-25).dp,0.dp)
                            )
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
                        modifier = Modifier.padding(5.dp)
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    SwipeRefresh(state = swipeRefreshState, onRefresh = {
                        viewModel.onEvent(MoviesListingEvent.Refresh)
                    }, Modifier.weight(9f)) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {

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

                            listItems(name = "Trending Movies",Modifier.padding(20.dp))

                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp),
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                items(stateTrending.movies?.result?.size ?: 0) { i ->
                                    stateTrending.movies?.result?.get(i).let {


                                        val movie = it
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(1.dp)
                                        ) {
                                            val movieUrl =
                                                rememberAsyncImagePainter(
                                                    baseImageUrl + (movie?.imagePoster
                                                        ?: movie?.backdropPath)
                                                )
                                            val movieTitle = movie?.title
                                            val movieOrginalTitle = movie?.titleOriginal

                                            val voteRate = movie?.voteAverage ?: 0.0
                                            val id  = movie?.id ?: 0

                                            ImageCard(
                                                painter = movieUrl,
                                                movieTitle = movieTitle.toString(),
                                                modifier = Modifier,
                                                voteRate = voteRate,
                                                movieOriginalTitle = movieOrginalTitle.toString(),
                                                navigator = navigator,
                                                movieID = id
                                            )
                                        }
                                    }
                                }
                            }
                            listItems(name = "NowPlaying Movies",Modifier.padding(20.dp))
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
                                        val voteRate =
                                            state.movies?.result?.get(i)?.voteAverage ?: 0.0
                                        val movieOrginalTitle =
                                            state.movies?.result?.get(i)?.titleOriginal
                                        val id  = state.movies?.result?.get(i)?.id ?: 0


                                        ImageCard(
                                            painter = movieUrl,
                                            movieTitle = movieTitle.toString(),
                                            modifier = Modifier,
                                            voteRate = voteRate,
                                            movieOriginalTitle = movieOrginalTitle.toString(),
                                            navigator = navigator,
                                            movieID = id
                                        )
                                    }
                                }
                            }


                        }
                    }


                }


            }
        }
    }
}



















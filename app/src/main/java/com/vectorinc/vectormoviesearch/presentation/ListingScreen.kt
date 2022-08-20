package com.vectorinc.vectormoviesearch.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.presentation.movie_listings.ImageCard
import com.vectorinc.vectormoviesearch.presentation.movie_listings.MovieListingViewModel
import com.vectorinc.vectormoviesearch.presentation.search_screen.Loading
import com.vectorinc.vectormoviesearch.presentation.search_screen.LoadingSwipeClone
import com.vectorinc.vectormoviesearch.presentation.search_screen.ShimmerAnimation
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlueBlured
import com.vectorinc.vectormoviesearch.ui.theme.DarkDimLight
import com.vectorinc.vectormoviesearch.ui.theme.MinContrastOfPrimaryVsSurface
import com.vectorinc.vectormoviesearch.util.DynamicThemePrimaryColorsFromImage
import com.vectorinc.vectormoviesearch.util.contrastAgainst
import com.vectorinc.vectormoviesearch.util.rememberDominantColorState
import com.vectorinc.vectormoviesearch.util.verticalGradientScrim

@Destination
@Composable
fun ListingScreen(
    navigator: DestinationsNavigator,
    viewModel: MovieListingViewModel = hiltViewModel(),
    imageUrl : String,
    selected : String
) {
    val state = viewModel.state
    val surfaceColor = MaterialTheme.colors.surface

    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    val trending: LazyPagingItems<Result> = viewModel.trendingMoviesSource.collectAsLazyPagingItems()
    val nowPlaying: LazyPagingItems<Result> = viewModel.genreMoviesSource.collectAsLazyPagingItems()
    val topRated: LazyPagingItems<Result> = viewModel.topRatedSource.collectAsLazyPagingItems()
    val latest: LazyPagingItems<Result> = viewModel.getLatestSource.collectAsLazyPagingItems()





    if (trending.itemCount != 0) {
        DynamicThemePrimaryColorsFromImage(dominantColorState) {

            val selectedImageUrl = imageUrl

            // When the selected image url changes, call updateColorsFromImageUrl() or reset()
            LaunchedEffect(selectedImageUrl) {
                if (selectedImageUrl != null) {
                    dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
                    Log.d("Image", "$selectedImageUrl")
                } else {
                    dominantColorState.reset()
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .windowInsetsPadding(
                        WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                    )
                    .verticalGradientScrim(
                        color = MaterialTheme.colors.primary.copy(alpha = 0.4f),
                        startYPercentage = 0.95f,

                        endYPercentage = 0.0f
                    )
            ) {
                val appBarColor = Color.Red.copy(alpha = 0.0020f)

                Spacer(
                    Modifier
                        .background(appBarColor)
                        .fillMaxWidth()
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                )
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = appBarColor,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    title = {
                        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                            IconButton(
                                onClick = { navigator.popBackStack() },
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.search),

                                    )
                            }
                        }
                            Text(text = "$selected Movies", fontSize =  17.sp)
                            },


                    )
                when (selected) {
                    "Trending" -> SearchMovies(movies = trending, navigator = navigator)
                    "NowPlaying" -> SearchMovies(movies = nowPlaying, navigator = navigator)
                    "TopRated" -> SearchMovies(movies = topRated, navigator = navigator)
                    "Latest" -> SearchMovies(movies = latest, navigator = navigator)
                }




            }


        }
    }else{
        Loading()
    }
}


@Composable
fun SearchMovies(
    movies: LazyPagingItems<Result>,
    navigator: DestinationsNavigator,

) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original/"


    if(movies.itemCount == 0){

    }else{
        LazyVerticalGrid(
            GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),

            )  {
            items(movies.itemCount ?: 0) { i ->
                movies[i].let {

                    Log.d("Items in Movies", it?.title ?: "")
                    val movie = it
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(1.dp)
                    ) {
                        val movieUrl =
                            rememberAsyncImagePainter(
                                baseImageUrl + (movie?.imagePoster
                                    ?: movie?.backdropPath),
                                filterQuality =  FilterQuality.None
                            )
                        val movieTitle = movie?.title
                        val movieOrginalTitle = movie?.titleOriginal

                        val voteRate = movie?.voteAverage ?: 0.0
                        val id = movie?.id ?: 0

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
            movies.apply {
                when {
                    loadState.refresh is
                            LoadState.Loading -> {


                    }

                    loadState.append is
                            LoadState.Loading -> {
                        item { com.vectorinc.vectormoviesearch.presentation.search_screen.LoadingItem(visible = true) }


                    }
                    loadState.refresh is
                            LoadState.Error -> {

                    }
                    loadState.append is
                            LoadState.Error -> {

                        item { com.vectorinc.vectormoviesearch.presentation.search_screen.LoadingItem(visible = false) }


                    }
                    loadState.append is
                            LoadState.NotLoading -> {

                        item { com.vectorinc.vectormoviesearch.presentation.search_screen.LoadingItem(visible = false) }

                    }
                }
            }
        }


    }


}



@Composable
fun LoadingItem(visible: Boolean) {
    if (visible) {
        ShimmerAnimation()
    }
}



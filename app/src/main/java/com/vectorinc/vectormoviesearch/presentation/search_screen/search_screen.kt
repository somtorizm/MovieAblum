@file:OptIn(ExperimentalFoundationApi::class)

package com.vectorinc.vectormoviesearch.presentation.search_screen

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.presentation.movie_listings.MovieListingViewModel
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue
import com.vectorinc.vectormoviesearch.ui.theme.DarkPurple
import com.vectorinc.vectormoviesearch.ui.theme.MinContrastOfPrimaryVsSurface
import com.vectorinc.vectormoviesearch.util.DynamicThemePrimaryColorsFromImage
import com.vectorinc.vectormoviesearch.util.contrastAgainst
import com.vectorinc.vectormoviesearch.util.rememberDominantColorState
import com.vectorinc.vectormoviesearch.util.verticalGradientScrim
import kotlinx.coroutines.flow.Flow


@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun Search(
    navigator: DestinationsNavigator,
    moviesViewModel: MovieListingViewModel = hiltViewModel(),
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val scrollState = rememberLazyListState()
    val scrollUpState = viewModel.scrollUp.value
    var searchTxt by remember { mutableStateOf(state.searchQuery) }
    val surfaceColor = MaterialTheme.colors.surface
    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }

    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        val baseImageUrl = "https://image.tmdb.org/t/p/original/"

        val selectedImageUrl = baseImageUrl + moviesViewModel.state.movies?.result?.get(0)?.imagePoster

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

                    TextField(
                        value = searchTxt,
                        label = {
                            Text(text = "Search", color = Color.White)
                        },
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = DarkPurple
                        ),

                        onValueChange = {
                            searchTxt = it
                            viewModel.onEvent(
                                SearchListingEvent.OnSearchQueryChange(searchTxt)
                            )

                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Search...")
                        },
                        maxLines = 1,
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.ArrowBack,
                                contentDescription = "back Icon",
                                modifier = Modifier
                                    .clickable {
                                        navigator.popBackStack()
                                    }
                            )
                        },
                        trailingIcon = {
                            Icon(Icons.Default.Clear,
                                contentDescription = "clear text",
                                modifier = Modifier
                                    .clickable {
                                        searchTxt = ""
                                    }
                            )
                        }

                    )
                },

                )

            SearchMovies(movies = viewModel.user, navigator = navigator, scrollState, scrollUpState)

        }


    }
}
@Composable
fun SearchMovies(
    movies: Flow<PagingData<Result>>,
    navigator: DestinationsNavigator,
    scrollState: LazyListState,
    scrollUpState: Boolean?
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original/"


    val userListItems: LazyPagingItems<Result> = movies.collectAsLazyPagingItems()
    Log.d("Paging", "${userListItems.itemCount}")

    LazyVerticalGrid(
        GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),

    ) {

        items(userListItems.itemCount) { i ->

            userListItems[i].let {
                val item = it
                val movieUrl =
                    rememberAsyncImagePainter(
                        baseImageUrl + (item?.imagePoster ?: item?.backdropPath)
                    )
                ImageCardRounded(
                    painter = movieUrl,
                    movieTitle = item?.title.toString(),
                    voteRate = item?.voteAverage ?: 0.0,
                    modifier = Modifier,
                    result = item, navigator = navigator,
                    movieId = item?.id ?: 0
                )

            }
        }
        userListItems.apply {
            when {
                loadState.refresh is
                        LoadState.Loading -> {


                }

                loadState.append is
                        LoadState.Loading -> {
                    item { LoadingItem(visible = true) }


                }
                loadState.refresh is
                        LoadState.Error -> {

                }
                loadState.append is
                        LoadState.Error -> {
                    item { LoadingItem(visible = false) }


                }
                loadState.append is
                        LoadState.NotLoading -> {
                    item { LoadingItem(visible = false) }

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

@Composable
fun persistedScrollState(viewModel: SearchViewModel): ScrollState {
    val scrollState = rememberScrollState(viewModel.scrollPosition)
    DisposableEffect(key1 = null) {
        onDispose {
            viewModel.scrollPosition = scrollState.value
        }
    }
    return scrollState
}





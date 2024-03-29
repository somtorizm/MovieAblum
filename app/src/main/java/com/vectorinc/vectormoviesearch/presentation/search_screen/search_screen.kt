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
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.presentation.movie_listings.ImageCard
import com.vectorinc.vectormoviesearch.presentation.movie_listings.MovieListingViewModel
import com.vectorinc.vectormoviesearch.ui.theme.*
import com.vectorinc.vectormoviesearch.util.DynamicThemePrimaryColorsFromImage
import com.vectorinc.vectormoviesearch.util.contrastAgainst
import com.vectorinc.vectormoviesearch.util.rememberDominantColorState
import com.vectorinc.vectormoviesearch.util.verticalGradientScrim
import kotlinx.coroutines.flow.Flow


@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun Search(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel(),
    imageUrl : String
) {
    val state = viewModel.state
    val searchVisibility = viewModel.searchVisiblity.value
    var searchTxt by remember { mutableStateOf(state.searchQuery) }
    val surfaceColor = MaterialTheme.colors.surface

    val dominantColorState = rememberDominantColorState { color ->
        // We want a color which has sufficient contrast against the surface color
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }


    DynamicThemePrimaryColorsFromImage(dominantColorState) {
        val baseImageUrl = "https://image.tmdb.org/t/p/original/"

        val selectedImageUrl = baseImageUrl + imageUrl

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

                            Text(text = "Search...", fontSize = 12.sp)

                        },
                        textStyle = TextStyle(fontSize = 15.sp),
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
            Spacer(modifier = Modifier.height(10.dp))
           LoadingSpinner(searchVisibility!!)

            SearchMovies(movies = viewModel.user, navigator = navigator)

        }


    }
}
@Composable
fun SearchMovies(
    movies: Flow<PagingData<Result>>,
    navigator: DestinationsNavigator,

    ) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original/"


    val userListItems: LazyPagingItems<Result> = movies.collectAsLazyPagingItems()
    Log.d("Paging", "${userListItems.itemCount}")

    if(userListItems.itemCount == 0){
        SearchIconGray()
    }else{
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
                            baseImageUrl + (item?.imagePoster ?: item?.backdropPath),
                            filterQuality = FilterQuality.None,
                            error =  painterResource(id = R.drawable.ic_image_gallery_svgrepo_com),
                            contentScale = ContentScale.Crop



                        )

                    ImageCard(
                        painter = movieUrl,
                        movieTitle = item?.title.toString(),
                        voteRate = item?.voteAverage ?: 0.0,
                        modifier = Modifier,
                        movieOriginalTitle = item?.titleOriginal ?: "",
                        navigator = navigator,
                        movieID = item?.id ?: 0
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


}



@Composable
fun LoadingItem(visible: Boolean) {
    if (visible) {
        ShimmerAnimation()
    }
}
@Composable
fun LoadingSpinner(visible: Boolean) {
    if (visible) {
        LoadingSwipeClone()
    }
}


@Composable
fun SearchIconGray(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {

            Image(
                painter = painterResource(id = R.drawable.ic_baseline_search_24_gray),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = "Search CreaView",
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp,
                color = DarkBlueBlured,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "Find your favorite movies, Tv shows and authors",
                fontWeight = FontWeight.Normal,
                color = DarkBlueBlured,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp
            )

        }
    }
}






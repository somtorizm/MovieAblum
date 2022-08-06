package com.vectorinc.vectormoviesearch.presentation.show_movie

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.presentation.OfflineDialog
import com.vectorinc.vectormoviesearch.presentation.movie_listings.RatingBarItem
import com.vectorinc.vectormoviesearch.presentation.search_screen.Loading
import com.vectorinc.vectormoviesearch.ui.theme.DarkDimLight
import com.vectorinc.vectormoviesearch.ui.theme.MinContrastOfPrimaryVsSurface
import com.vectorinc.vectormoviesearch.util.DynamicThemePrimaryColorsFromImage
import com.vectorinc.vectormoviesearch.util.contrastAgainst
import com.vectorinc.vectormoviesearch.util.rememberDominantColorState
import com.vectorinc.vectormoviesearch.util.verticalGradientScrim
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Destination
@Composable
fun PreviewScreen(
    movieID: Int,
    navigator: DestinationsNavigator,
    viewModel: PreviewViewModel = hiltViewModel()
) {
    val surfaceColor = MaterialTheme.colors.surface
    val dominantColorState = rememberDominantColorState { color ->
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    val state = viewModel.state
    if (!state.isLoading) {
        Loading()
    } else {
        if (state.error == true) {
            OfflineDialog {
                viewModel.onEvent(PreviewEvents.Refresh)
            }
        } else {
            val size = state.movies?.genres?.size ?: 0

            val movieUrlBackDrop =
                rememberAsyncImagePainter(
                    "https://image.tmdb.org/t/p/original" + state.movies?.backdrop_path
                )
            val movieUrlImage =
                rememberAsyncImagePainter(
                    "https://image.tmdb.org/t/p/original" + state.movies?.poster_path
                )
            DynamicThemePrimaryColorsFromImage(dominantColorState) {
                val baseImageUrl = "https://image.tmdb.org/t/p/original/"
                val image =
                    if (state.movies?.backdrop_path == "null") state.movies.poster_path else state.movies?.backdrop_path
                val selectedImageUrl by remember { mutableStateOf(baseImageUrl + image) }


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
                            color = MaterialTheme.colors.primary.copy(0.2f),
                            startYPercentage = 0.0f,

                            endYPercentage = 0.6f
                        )
                ) {

                    Row(
                        Modifier
                            .fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .offset(0.dp, (-60).dp)
                                .fillMaxWidth(0.5f)
                                .fillMaxHeight(0.4f)
                                .padding(horizontal = 15.dp)

                        ) {

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(15.dp),
                                elevation = 8.dp
                            ) {
                                Box(
                                    modifier = Modifier

                                ) {
                                    Image(
                                        modifier = Modifier.fillMaxWidth(),
                                        painter = movieUrlImage,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop
                                    )

                                }

                            }

                        }

                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text(
                                text = state.movies?.title ?: "",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.height(5.dp))

                            Text(
                                text = convertToDate(state.movies?.release_date ?: ""),
                                fontWeight = FontWeight.Thin,
                                fontSize = 12.sp,
                                color = MaterialTheme.colors.primary
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                items(size) { i ->
                                    state.movies?.genres?.get(i).let {
                                        Text(
                                            text = it?.name ?: "",
                                            fontWeight = FontWeight.SemiBold,
                                            fontSize = 8.sp,
                                            color = Color.White,
                                            modifier = Modifier
                                                .background(Color.DarkGray, RoundedCornerShape(30))
                                                .padding(9.dp)

                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = state.movies?.status ?: "",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 8.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .background(MaterialTheme.colors.primary, RoundedCornerShape(30))
                                    .padding(9.dp)

                            )
                            Spacer(modifier = Modifier.height(10.dp))




                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_movie_icon),
                                    contentDescription = stringResource(com.vectorinc.vectormoviesearch.R.string.search),
                                    modifier = Modifier.size(35.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                RatingBarItem(
                                    voteRate = state.movies?.vote_average ?: 0.0,
                                    number = 100,
                                    radius = 12.dp,
                                    color = Color.Yellow,
                                    fontSize = 10.sp,
                                    modifier = Modifier
                                )
                            }
                            Spacer(modifier = Modifier.height(5.dp))


                        }


                    }

                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(15.dp)
                            .offset(0.dp, (-40).dp)
                    ) {
                        Text(
                            text = state.movies?.overview ?: "",
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                        )
                        Log.d("Tab size", "${state.movieCategories.size}")
                        if (state.movieCategories.isNotEmpty()) {
                            MovieCategoryTabs(
                                categories = state.movieCategories,
                                selectedCategory = state.selectedMovieCategory,
                                onCategorySelected = viewModel::onMovieCategorySelected
                            )
                        }

                        when (state.selectedMovieCategory) {
                            PreviewViewModel.MovieCategory.Cast -> {
                                // TODO
                                Log.d("Cast","Preview")

                            }
                            PreviewViewModel.MovieCategory.Crew -> {
                                Log.d("Crew","Preview")
                            }
                        }

                    }


                }


            }

        }
    }
}

@Composable
private fun MovieCategoryTabs(
    categories: List<PreviewViewModel.MovieCategory>,
    selectedCategory: PreviewViewModel.MovieCategory,
    onCategorySelected: (PreviewViewModel.MovieCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = categories.indexOfFirst { it == selectedCategory }
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        MovieCategoryTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }

    TabRow(
        selectedTabIndex = selectedIndex,
        indicator = indicator,
        modifier = modifier
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = index == selectedIndex,
                onClick = { onCategorySelected(category) },
                text = {
                    Text(
                        text = when (category) {
                            PreviewViewModel.MovieCategory.Cast-> stringResource(R.string.cast)
                            PreviewViewModel.MovieCategory.Crew -> stringResource(R.string.crew)
                        },
                        style = MaterialTheme.typography.body2
                    )
                }
            )
        }
    }
}

@Composable
fun MovieCategoryTabIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.onSurface
) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(color, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}

fun convertToDate(date: String): String {
    if (date.toString().isBlank()) return ""
    val date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return "${date.month}" + " " + "${date.year}"
}










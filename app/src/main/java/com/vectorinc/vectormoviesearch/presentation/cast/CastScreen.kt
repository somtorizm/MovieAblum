package com.vectorinc.vectormoviesearch.presentation.cast

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
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
import com.vectorinc.vectormoviesearch.presentation.show_movie.ImagePreview
import com.vectorinc.vectormoviesearch.ui.theme.DarkDimLight
import com.vectorinc.vectormoviesearch.ui.theme.MinContrastOfPrimaryVsSurface
import com.vectorinc.vectormoviesearch.util.DynamicThemePrimaryColorsFromImage
import com.vectorinc.vectormoviesearch.util.contrastAgainst
import com.vectorinc.vectormoviesearch.util.rememberDominantColorState
import com.vectorinc.vectormoviesearch.util.verticalGradientScrim


@Destination
@Composable
fun CastScreen(
    personId: Int,
    navigator: DestinationsNavigator,
    viewModel: CastViewModel = hiltViewModel()
) {
    val surfaceColor = MaterialTheme.colors.surface
    val dominantColorState = rememberDominantColorState { color ->
        color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
    }
    val state = viewModel.state

    val scope = rememberCoroutineScope()
    if (!state.isLoading) {

        Loading()
    } else {
        if (state.error == true) {
            OfflineDialog(title = stringResource(id = R.string.connection_error_title),
                message = stringResource(
                    id = R.string.connection_error_message
                ),
                onRetry = {
                    viewModel.onEvent(CastEvents.Refresh)
                }, navigator = navigator, onBack = {
                    navigator.popBackStack()

                })
        } else {
            if (state.cast == null) {
                OfflineDialog(title = stringResource(id = R.string.movie_unavailable),
                    message = stringResource(
                        id = R.string.movie_unavailable_message
                    ),
                    onRetry = {
                        viewModel.onEvent(CastEvents.Refresh)
                    }, navigator = navigator, onBack = {
                        navigator.popBackStack()
                    })
            } else {

            }


            val movieUrlBackDrop =
                rememberAsyncImagePainter(
                    "https://image.tmdb.org/t/p/original" + state.cast?.profile_path
                )
            val movieUrlImage =
                rememberAsyncImagePainter(
                    "https://image.tmdb.org/t/p/original" + state.cast?.profile_path,
                    error = painterResource(id = R.drawable.ic_image_gallery_svgrepo_com)
                )
            DynamicThemePrimaryColorsFromImage(dominantColorState) {
                val baseImageUrl = "https://image.tmdb.org/t/p/original/"
                val image = state.cast?.profile_path
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
                        .verticalScroll(rememberScrollState())
                        .windowInsetsPadding(
                            WindowInsets.systemBars.only(WindowInsetsSides.Horizontal)
                        )
                        .verticalGradientScrim(
                            color = MaterialTheme.colors.primary.copy(0.2f),
                            startYPercentage = 0.0f,

                            endYPercentage = 0.6f
                        )
                ) {
                    AppBar(movieUrlBackDrop = movieUrlBackDrop, navigator = navigator)
                    TitleBody(
                        movieUrlImage = movieUrlImage,
                        viewModel = viewModel,
                    )
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(15.dp)
                            .offset(0.dp, (-40).dp)
                    )
                    {
                        val bio = if(state.cast?.biography.isNullOrBlank())  "No Biography" else state.cast?.biography ?: ""
                        var isExpanded by remember { mutableStateOf(false) }
                        Text(
                            text =  bio,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.clickable {
                                isExpanded = !isExpanded
                            },
                            maxLines = if (isExpanded) Int.MAX_VALUE else 5,

                            )
                        Text(
                            text = if (isExpanded) "Read less" else "Read more",
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp,
                            color = MaterialTheme.colors.secondary,
                            modifier = Modifier.clickable {
                                isExpanded = !isExpanded
                            },

                            )
                        Spacer(modifier = Modifier.height(10.dp))


                    }
                }


            }


        }
    }
}


@Composable
fun AppBar(movieUrlBackDrop: Painter, navigator: DestinationsNavigator) {
    Box(Modifier.height(200.dp)) {
        ImagePreview(
            painter = movieUrlBackDrop,
            modifier = Modifier.fillMaxWidth(),
            color = Color.Transparent,
        )

        Column() {

            Spacer(
                Modifier
                    .fillMaxWidth()
                    .windowInsetsTopHeight(WindowInsets.statusBars)
            )
            TopAppBar(
                elevation = 0.dp,
                backgroundColor = Color.Transparent,
                title = {
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {

                        IconButton(
                            onClick = { navigator.popBackStack() },
                            Modifier.background(
                                DarkDimLight,
                                RoundedCornerShape(100)
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.search),

                                )
                        }
                    }
                },

                )
        }


    }
}

@Composable
fun TitleBody(
    movieUrlImage: Painter,
    viewModel: CastViewModel
) {
    val state = viewModel.state
    Row(
        Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .offset(0.dp, (-60).dp)
                .width(200.dp)
                .height(250.dp)
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
                text = state.cast?.name ?: "",
                fontWeight = FontWeight.Bold,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = state.cast?.birthday ?: "",
                fontWeight = FontWeight.Thin,
                fontSize = 12.sp,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Other names" ?: "",
                fontWeight = FontWeight.Thin,
                fontSize = 12.sp,
                color = MaterialTheme.colors.secondary
            )
            Spacer(modifier = Modifier.height(10.dp))
            val size = state.cast?.also_known_as?.size ?: 0
            LazyRow(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                items(size) { i ->
                    state.cast?.also_known_as?.get(i).let {
                        Text(
                            text = it ?: "",
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
                text = state.cast?.known_for_department ?: "",
                fontWeight = FontWeight.SemiBold,
                fontSize = 8.sp,
                color = Color.White,
                modifier = Modifier
                    .background(
                        MaterialTheme.colors.primary,
                        RoundedCornerShape(30)
                    )
                    .padding(9.dp)

            )
            Spacer(modifier = Modifier.width(10.dp))

        }
        Spacer(modifier = Modifier.height(10.dp))


    }


}







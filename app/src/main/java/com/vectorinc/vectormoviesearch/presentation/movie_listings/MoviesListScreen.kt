package com.vectorinc.vectormoviesearch.presentation.movie_listings

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.R
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.presentation.AboutUsDialog
import com.vectorinc.vectormoviesearch.presentation.MovieAppState
import com.vectorinc.vectormoviesearch.presentation.OfflineDialog
import com.vectorinc.vectormoviesearch.presentation.destinations.ListingScreenDestination
import com.vectorinc.vectormoviesearch.presentation.destinations.SearchDestination
import com.vectorinc.vectormoviesearch.presentation.destinations.ShowMoviesDestination
import com.vectorinc.vectormoviesearch.presentation.rememberMoviesAppState
import com.vectorinc.vectormoviesearch.presentation.search_screen.Loading
import com.vectorinc.vectormoviesearch.presentation.search_screen.LoadingItem
import com.vectorinc.vectormoviesearch.ui.theme.DarkBlue
import com.vectorinc.vectormoviesearch.ui.theme.MinContrastOfPrimaryVsSurface
import com.vectorinc.vectormoviesearch.util.*
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Destination()
@Composable
fun ShowMovies(
    navigator: DestinationsNavigator,
    appState: MovieAppState = rememberMoviesAppState(),
    viewModel: MovieListingViewModel = hiltViewModel()
) {
    val baseImageUrl = "https://image.tmdb.org/t/p/original/"
    val trending: LazyPagingItems<Result> =
        viewModel.trendingMoviesSource.collectAsLazyPagingItems()
    val genres: LazyPagingItems<Result> = viewModel.genreMoviesSource.collectAsLazyPagingItems()
    val topRated: LazyPagingItems<Result> = viewModel.topRatedSource.collectAsLazyPagingItems()
    val getLatest: LazyPagingItems<Result> = viewModel.getLatestSource.collectAsLazyPagingItems()

    val scope = rememberCoroutineScope()







    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )


    if (!viewModel.loading.value!!) {
        Loading()
        if (trending.itemCount != 0) viewModel.setLoading(true)
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

        }
    } else {
        val surfaceColor = MaterialTheme.colors.surface
        val dominantColorState = rememberDominantColorState { color ->
            // We want a color which has sufficient contrast against the surface color
            color.contrastAgainst(surfaceColor) >= MinContrastOfPrimaryVsSurface
        }


        if (trending.itemCount != 0 && trending.itemCount != null) {

            DynamicThemePrimaryColorsFromImage(dominantColorState) {
                val baseImageUrl = "https://image.tmdb.org/t/p/original/"


                val selectedImageUrl = baseImageUrl + trending[0]?.imagePoster

                // When the selected image url changes, call updateColorsFromImageUrl() or reset()
                LaunchedEffect(selectedImageUrl) {
                    if (selectedImageUrl != null) {
                        dominantColorState.updateColorsFromImageUrl(selectedImageUrl)
                        Log.d("Image", "$selectedImageUrl")
                    } else {
                        dominantColorState.reset()
                    }
                }
                val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
                val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
                val color = MaterialTheme.colors.primary.copy(alpha = 0.4f)
                BottomSheetScaffold(
                    scaffoldState = scaffoldState,
                    sheetPeekHeight = 0.dp,
                    sheetContent = {

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center) {


                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center

                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_drag_handle),
                                        contentDescription = stringResource(com.vectorinc.vectormoviesearch.R.string.search),
                                        modifier = Modifier.size(35.dp)
                                    )
                                }
                                Spacer(Modifier.height(5.dp))
                                Text(
                                    text = "About Us",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.secondary,


                                )
                                Privacy()



                                }


                            }


                        },

                    sheetBackgroundColor = DarkBlue,
                    sheetElevation = 10.dp,
                    sheetShape = RoundedCornerShape(5)
                ) {
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
                                    modifier = Modifier.offset((-25).dp, 0.dp)
                                )
                            },

                            backgroundColor = appBarColor,
                            actions = {
                                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.high) {
                                    IconButton(
                                        onClick = {
                                            navigator.navigate(
                                                SearchDestination(selectedImageUrl)
                                            )
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            modifier = Modifier.size(35.dp),
                                            contentDescription = stringResource(com.vectorinc.vectormoviesearch.R.string.search)
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                if (sheetState.isExpanded) sheetState.collapse() else sheetState.expand()

                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.MoreVert,
                                            modifier = Modifier.size(35.dp),
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

                                if (trending.itemCount != 0) {
                                    viewModel.setLoading(true)
                                    var pos = 0;
                                    if (trending[0]?.titleOriginal.isNullOrBlank()) pos++
                                    val titleOriginal =
                                        if (trending[pos]?.titleOriginal.isNullOrBlank()) "" else trending[pos]?.title
                                    val title =
                                        if (trending[pos]?.title.isNullOrBlank()) "" else trending[pos]?.titleOriginal
                                    val imageUrl = trending[pos]?.imagePoster
                                    val movieTitle = title
                                    val movieOriginalTitle = titleOriginal
                                    val voteRate = trending[pos]?.voteAverage ?: 0.0
                                    val item = trending[pos]

                                    val movieDescription = trending[pos]?.description
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

                                listItems(
                                    name = "NowPlaying Movies",
                                    Modifier
                                        .padding(20.dp)
                                        .clickable {
                                            navigator.navigate(
                                                ListingScreenDestination(
                                                    imageUrl = selectedImageUrl,
                                                    selected = "NowPlaying"
                                                )
                                            )
                                        })

                                LazyRow(
                                    modifier = Modifier.padding(5.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(genres.itemCount ?: 0) { i ->
                                        genres[i].let {
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
                                    trending.apply {
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
                                listItems(
                                    name = "Trending Movies",
                                    Modifier
                                        .padding(20.dp)
                                        .clickable {
                                            navigator.navigate(
                                                ListingScreenDestination(
                                                    imageUrl = selectedImageUrl,
                                                    selected = "Trending"
                                                )
                                            )
                                        })
                                LazyRow(
                                    modifier = Modifier.padding(5.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(trending.itemCount ?: 0) { i ->
                                        trending[i].let {
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
                                    trending.apply {
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
                                listItems(
                                    name = "TopRated Movies",
                                    Modifier
                                        .padding(20.dp)
                                        .clickable {
                                            navigator.navigate(
                                                ListingScreenDestination(
                                                    imageUrl = selectedImageUrl,
                                                    selected = "TopRated"
                                                )
                                            )
                                        })

                                LazyRow(
                                    modifier = Modifier.padding(5.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(topRated.itemCount ?: 0) { i ->
                                        topRated[i].let {
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
                                    trending.apply {
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
                                listItems(
                                    name = "Latest Movies",
                                    Modifier
                                        .padding(15.dp)
                                        .clickable {
                                            navigator.navigate(
                                                ListingScreenDestination(
                                                    imageUrl = selectedImageUrl,
                                                    selected = "Latest"
                                                )
                                            )
                                        })

                                LazyRow(
                                    modifier = Modifier.padding(5.dp),
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    items(getLatest.itemCount ?: 0) { i ->
                                        getLatest[i].let {
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
                                    trending.apply {
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


                    }
                }


            }
        }
    }

}


@Composable
fun Privacy() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        val context = LocalContext.current

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(onClick = { moveToPrivacy(context)}, ) {
                Text(
                    text = "Privacy and Policy",
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 12.sp
                )
            }
            Button(onClick = { openLinkedInPage(context) }, ) {
                Text(
                    text = "Contact Developer",
                    fontSize = 12.sp
                )
            }



        }
    }
}




















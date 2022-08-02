@file:OptIn(ExperimentalFoundationApi::class)

package com.vectorinc.vectormoviesearch.presentation.search_screen

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.ui.theme.DarkPurple


@com.ramcosta.composedestinations.annotation.Destination
@Composable
fun Search(
    navigator: DestinationsNavigator,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state = viewModel.state
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            value = state.searchQuery,
            label = {
                Text(text = "Search", color = Color.White)
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DarkPurple
            ),
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            },
            onValueChange = {
                viewModel.onEvent(
                    SearchListingEvent.OnSearchQueryChange(it)

                )

            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )
        val baseImageUrl = "https://image.tmdb.org/t/p/original/"


        val userListItems: LazyPagingItems<Result> = viewModel.user.collectAsLazyPagingItems()
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
                    Log.d("Loading", "${state.isLoading}")


                    val movieUrl =
                        rememberAsyncImagePainter(
                            baseImageUrl + item?.imagePoster
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
                userListItems.apply {
                    when{
                        loadState.append is LoadState.Loading -> {
                          LoadingItem()
                        }
                    }
                }
            }

        }
    }


}
@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier =
        Modifier
            .testTag("ProgressBarItem")
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            ),
        color = Color.Blue
    )
}





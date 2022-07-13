package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.ramcosta.composedestinations.annotation.Destination


@Composable
@Destination(start = true)
fun ShowMovies(
    viewModel: MovieListingViewModel = hiltViewModel()
) {

    val state = viewModel.state
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = viewModel.state.isRefreshing
    )
    Column(modifier = Modifier.fillMaxSize()) {
        
        Text(text = state.movies?.result?.get(0)?.description ?: "")
    }

}





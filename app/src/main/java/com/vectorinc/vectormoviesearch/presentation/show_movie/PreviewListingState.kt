package com.vectorinc.vectormoviesearch.presentation.show_movie

import com.vectorinc.vectormoviesearch.domain.model.MoviesDiscover
import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing

data class PreviewListingState(
    val movies: MoviesDiscover? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
)
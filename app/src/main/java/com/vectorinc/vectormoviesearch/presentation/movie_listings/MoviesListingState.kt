package com.vectorinc.vectormoviesearch.presentation.movie_listings

import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing

data class MoviesListingState(
    val movies : MoviesGenreListing? = null,
    val isLoading : Boolean = false,
    val isRefreshing : Boolean = false,
    val error : String? = null
)
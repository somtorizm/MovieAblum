package com.vectorinc.vectormoviesearch.presentation.search_screen

import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing

data class SearchListingState(
    val movies: MoviesGenreListing? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val endReached : Boolean = false,
    val page : Int = 0
)

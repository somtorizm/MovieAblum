package com.vectorinc.vectormoviesearch.presentation.show_movie

import com.vectorinc.vectormoviesearch.domain.model.MovieCredit
import com.vectorinc.vectormoviesearch.domain.model.MoviesDiscover

data class PreviewListingState(
    val movies: MoviesDiscover? = null,
    val moviesCredit : MovieCredit? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: Boolean? = false,
    val selectedMovieCategory: PreviewViewModel.MovieCategory = PreviewViewModel.MovieCategory.Cast,
    val movieCategories: List<PreviewViewModel.MovieCategory> = emptyList()
)
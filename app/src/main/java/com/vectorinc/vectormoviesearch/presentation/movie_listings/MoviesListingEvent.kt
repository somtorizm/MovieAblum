package com.vectorinc.vectormoviesearch.presentation.movie_listings

sealed class MoviesListingEvent {
    object Refresh : MoviesListingEvent()
}

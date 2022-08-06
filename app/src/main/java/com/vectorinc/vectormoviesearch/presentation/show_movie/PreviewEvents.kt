package com.vectorinc.vectormoviesearch.presentation.show_movie

import com.vectorinc.vectormoviesearch.presentation.movie_listings.MoviesListingEvent

sealed class PreviewEvents{
    object Refresh : PreviewEvents()
}

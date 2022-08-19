package com.vectorinc.vectormoviesearch.presentation.cast

import com.vectorinc.vectormoviesearch.presentation.movie_listings.MoviesListingEvent

sealed class CastEvents{
    object Refresh : CastEvents()
}

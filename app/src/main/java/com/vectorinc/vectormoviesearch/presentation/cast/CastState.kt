package com.vectorinc.vectormoviesearch.presentation.cast

import com.vectorinc.vectormoviesearch.domain.model.*

data class CastState(
    val cast: CastPerson? = null,
    val featuredMovies:  ActorMoviesFeature? = null,

    val images : CastImages? = null,
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: Boolean? = false,
)
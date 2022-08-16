package com.vectorinc.vectormoviesearch.presentation.search_screen

sealed class SearchListingEvent {
    object Refresh: SearchListingEvent()
    data class OnSearchQueryChange(val query: String): SearchListingEvent()
    class Success()

    object Loading : SearchListingEvent()
    object Empty : SearchListingEvent()
}
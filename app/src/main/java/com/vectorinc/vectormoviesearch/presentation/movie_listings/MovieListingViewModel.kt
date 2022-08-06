package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import com.vectorinc.vectormoviesearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListingViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val api: MoviesApi,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var state by mutableStateOf(MoviesListingState())
    var stateTrending by mutableStateOf(MoviesListingState())


    init {
        getMoviesListings()
        getTrendingListings()

    }


    fun onEvent(event: MoviesListingEvent) {
        when (event) {
            is MoviesListingEvent.Refresh -> {
                getMoviesListings(fetchFromRemote = true)
                getTrendingListings(fetchFromRemote = true)


            }
        }
    }

    private fun getMoviesListings(
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {

            state = state.copy(isLoading = false)
            repository.getMoviesGenre(fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->

                            state = state.copy(
                                movies = listings
                            )
                            state = state.copy(
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Error -> {

                    }
                    is Resource.isLoading -> {
                    }

                }

            }
        }

    }

    private fun getTrendingListings(
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {

            repository.getMoviesTrending(fetchFromRemote).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { listings ->

                            stateTrending = stateTrending.copy(
                                movies = listings
                            )
                        }
                    }
                    is Resource.Error -> {

                    }
                    is Resource.isLoading -> {
                    }

                }

            }
        }

    }


}
package com.vectorinc.vectormoviesearch.presentation.show_movie

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vectorinc.vectormoviesearch.data.pagination.ReviewPagingSource
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.ResultReview
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import com.vectorinc.vectormoviesearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository,
    private val api: MoviesApi
) : ViewModel() {

    enum class MovieCategory {
        Cast, Crew
    }

    private var selectedCategory = MovieCategory.Cast
    private val categories = MovieCategory.values().asList()


    var state by mutableStateOf(PreviewListingState())
    val movieId = savedStateHandle.get<Int>("movieID") ?: 0

    val reviews: Flow<PagingData<ResultReview>> = Pager(PagingConfig(pageSize = 20)) {
        ReviewPagingSource(api, movieId)
    }.flow.cachedIn(viewModelScope)

    init {
        getPreviewMovies()
        getMovieCredit()
        getMoviesThumbNail()
        state = state.copy(
            movieCategories = categories,
            selectedMovieCategory = selectedCategory,
        )

    }

    fun onMovieCategorySelected(category: MovieCategory) {
        state = state.copy(selectedMovieCategory = category)
    }

    fun getPreviewMovies() {
        state = state.copy(isLoading = false)
        state = state.copy(error = false)

        viewModelScope.launch {
            val movieId = savedStateHandle.get<Int>("movieID") ?: return@launch
            repository.getMovieSelected(movieId).collect {
                when (it) {
                    is Resource.Error -> {
                        Log.d("View Model State", "Error")
                        state = state.copy(error = true)


                    }
                    is Resource.Success -> {
                        state = state.copy(movies = it.data)
                        delay(600)
                        state = state.copy(isLoading = true)
                        Log.d("View Model State", "Successful")


                    }
                    is Resource.isLoading -> {
                        Log.d("View Model State", "Loading")
                    }
                }
            }

        }
    }


    private fun getMovieCredit() {
        viewModelScope.launch {
            val movieId = savedStateHandle.get<Int>("movieID") ?: return@launch
            repository.getMoviesCredit(movieId).collect {
                when (it) {
                    is Resource.Error -> {
                        Log.d("View Model State", "Error")


                    }
                    is Resource.Success -> {
                        state = state.copy(moviesCredit = it.data)
                        Log.d("View Model State", "Successful")


                    }
                    is Resource.isLoading -> {
                        Log.d("View Model State", "Loading")
                    }
                }
            }


        }
    }

    private fun getMoviesThumbNail() {
        viewModelScope.launch {
            val movieId = savedStateHandle.get<Int>("movieID") ?: return@launch
            repository.getThumbNail(movieId).collect {
                when (it) {
                    is Resource.Error -> {
                        Log.d("View Model State", "Error")


                    }
                    is Resource.Success -> {
                        state = state.copy(thumbNails = it.data)
                        Log.d("View Model State", "Successful")


                    }
                    is Resource.isLoading -> {
                        Log.d("View Model State", "Loading")
                    }
                }
            }


        }

    }

    fun onEvent(event: PreviewEvents) {
        when (event) {
            PreviewEvents.Refresh -> getPreviewMovies()
        }
    }


}
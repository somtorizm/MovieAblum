package com.vectorinc.vectormoviesearch.presentation.show_movie

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import com.vectorinc.vectormoviesearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository
) : ViewModel() {


    var state by mutableStateOf(PreviewListingState())

    init {
        state = state.copy(isLoading = false)
        viewModelScope.launch {
            val movieId = savedStateHandle.get<Int>("movieID") ?: return@launch
            repository.getMovieSelected(movieId).collect {
                when (it) {
                    is Resource.Error -> {
                        Log.d("View Model State", "Error")


                    }
                    is Resource.Success -> {
                        state = state.copy(movies = it.data)
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


}
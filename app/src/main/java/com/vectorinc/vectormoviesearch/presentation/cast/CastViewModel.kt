package com.vectorinc.vectormoviesearch.presentation.cast

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import com.vectorinc.vectormoviesearch.presentation.show_movie.PreviewEvents
import com.vectorinc.vectormoviesearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: MoviesRepository,
) : ViewModel() {

    var state by mutableStateOf(CastState())

    val personId = savedStateHandle.get<Int>("personId") ?: 0

    init {
        getCast(personId)
    }

    fun getCast(id: Int) {
        viewModelScope.launch {
            repository.getCastInfo(personId).collect() {
                when (it) {
                    is Resource.Error -> state = state.copy(error = true)
                    is Resource.Success -> {
                        state = state.copy(cast = it.data)
                        state = state.copy(isLoading = true)
                    }
                    is Resource.isLoading -> TODO()
                }
            }
        }
    }

    fun onEvent(event: CastEvents) {
        when (event) {
            CastEvents.Refresh -> getCast(personId)
        }
    }


}
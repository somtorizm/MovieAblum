package com.vectorinc.vectormoviesearch.presentation.search_screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import com.vectorinc.vectormoviesearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository
) : ViewModel() {
    companion object {
        val TAG = "View Model Class"
    }
    var state by mutableStateOf(SearchListingState())


    private var searchJob: Job? = null



    fun search(query: String, page: Int) {
        viewModelScope.launch {
            val result = repository.getSearchMovies(query, page)
            result.collect{result ->
                when(result){
                    is Resource.Error -> {
                        state = state.copy(error = result.message)

                    }
                    is Resource.Success -> {
                        state = state.copy(isLoading = true)
                        state = state.copy(movies = result.data)




                    }
                    is Resource.isLoading -> {

                    }
                }

            }
        }
    }




    fun onEvent(event: SearchListingEvent) {
        when (event) {
            is SearchListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    search(state.searchQuery, page = 1)
                }
            }
            SearchListingEvent.Refresh -> TODO()
            else -> {

            }
        }

    }


}
package com.vectorinc.vectormoviesearch.presentation.search_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vectorinc.vectormoviesearch.data.pagination.MoviesPagingSource
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val api: MoviesApi
) : ViewModel() {


    var state by mutableStateOf(SearchListingState())
    private var searchJob: Job? = null

    private var lastScrollIndex = 0

    private val _scrollUp = MutableLiveData(false)
    val scrollUp: LiveData<Boolean>
        get() = _scrollUp

    fun updateScrollPosition(newScrollIndex: Int) {
        if (newScrollIndex == lastScrollIndex) return

        _scrollUp.value = newScrollIndex > lastScrollIndex
        lastScrollIndex = newScrollIndex
    }

    fun onEvent(event: SearchListingEvent) {
        when (event) {
            is SearchListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(200L)
                    user = Pager(PagingConfig(pageSize = 20)) {
                        MoviesPagingSource(api, state.searchQuery)
                    }.flow


                }
            }
            SearchListingEvent.Refresh -> TODO()
            else -> {

            }
        }

    }

    var user: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
        MoviesPagingSource(api, state.searchQuery)
    }.flow




}
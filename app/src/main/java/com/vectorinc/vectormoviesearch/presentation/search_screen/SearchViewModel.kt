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
import androidx.paging.cachedIn
import com.vectorinc.vectormoviesearch.data.pagination.MoviesPagingSource
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val api: MoviesApi
) : ViewModel() {


    var state by mutableStateOf(SearchListingState())
    private var searchJob: Job? = null
    var scrollPosition = 0

    private var lastScrollIndex = 0

    private val _searchVisiblity = MutableLiveData(false)
    val searchVisiblity: LiveData<Boolean>
        get() = _searchVisiblity

    var user: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
        MoviesPagingSource(api, state.searchQuery)
    }.flow.cachedIn(viewModelScope)
    
    fun setSearchVisibility(boolean: Boolean){
        _searchVisiblity.value =  boolean
    }

    

    fun onEvent(event: SearchListingEvent) {
        when (event) {
            is SearchListingEvent.OnSearchQueryChange -> {
                state = state.copy(searchQuery = event.query)
                user = Pager(PagingConfig(pageSize = 20)) {
                        MoviesPagingSource(api, state.searchQuery)
                    }.flow.cachedIn(viewModelScope)

            }
            SearchListingEvent.Refresh -> TODO()
            else -> {

            }
        }

    }





}
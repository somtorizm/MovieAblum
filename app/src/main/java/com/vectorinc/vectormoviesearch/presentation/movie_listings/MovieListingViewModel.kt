package com.vectorinc.vectormoviesearch.presentation.movie_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vectorinc.vectormoviesearch.data.pagination.*
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import com.vectorinc.vectormoviesearch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListingViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val api: MoviesApi,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    var state by mutableStateOf(MoviesListingState())
    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean>
        get() = _loading


    init {


    }

    val trendingMoviesSource: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
        TrendingMoviesPagingSource(api)
    }.flow.cachedIn(viewModelScope)

    fun setLoading(boolean: Boolean){
        _loading.value = boolean
    }



    val genreMoviesSource: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
        GenersMoviesPagingSource(api)
    }.flow.cachedIn(viewModelScope)

    val topRatedSource: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
        TopRatedPagingSource(api)
    }.flow.cachedIn(viewModelScope)

    val getLatestSource: Flow<PagingData<Result>> = Pager(PagingConfig(pageSize = 20)) {
        GetLatestPaging(api)
    }.flow.cachedIn(viewModelScope)



    fun onEvent(event: MoviesListingEvent) {
        when (event) {
            is MoviesListingEvent.Refresh -> {


            }
        }
    }





}
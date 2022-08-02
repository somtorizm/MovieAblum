package com.vectorinc.vectormoviesearch.domain

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vectorinc.vectormoviesearch.data.mapper.toMoviesGenreListing
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing
import com.vectorinc.vectormoviesearch.domain.model.Result
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MoviesPagingSource(private val api: MoviesApi, private val query: String) :
    PagingSource<Int, Result>() {
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        val position = params.key ?: STARTING_PAGE_INDEX
        try {
            val response = api.searchMoviesListing(searchQuery = query, page = position)
            val nextKey = position + 1
            val result = response.body()?.toMoviesGenreListing()
            return LoadResult.Page(
                data = result?.result ?: emptyList(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
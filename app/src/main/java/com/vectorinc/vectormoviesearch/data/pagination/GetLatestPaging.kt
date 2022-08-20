package com.vectorinc.vectormoviesearch.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vectorinc.vectormoviesearch.data.mapper.toMoviesGenreListing
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.Result
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class GetLatestPaging(private val api: MoviesApi) :
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
            Log.d("Entered Pagination", "Yes")
            val response = api.getLatest( page = position)
            val nextKey = position + 1

            val result = response.body()?.toMoviesGenreListing()
            if (position > (result?.totalPages ?: 1)) {
                return LoadResult.Error(Exception())
            }
            return LoadResult.Page(
                data = result?.result ?: emptyList(),
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )

        } catch (exception: IOException) {
            Log.d("Error", "Loading Reached")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}
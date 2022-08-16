package com.vectorinc.vectormoviesearch.data.pagination

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vectorinc.vectormoviesearch.data.mapper.toMoviesGenreListing
import com.vectorinc.vectormoviesearch.data.mapper.toReview
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.domain.model.ResultReview
import com.vectorinc.vectormoviesearch.domain.model.Review
import kotlinx.coroutines.delay
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class ReviewPagingSource(private val api: MoviesApi, private val movieId: Int) :
    PagingSource<Int, ResultReview>() {
    override fun getRefreshKey(state: PagingState<Int, ResultReview>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultReview> {
        val position = params.key ?: STARTING_PAGE_INDEX
        try {
            Log.d("Entered Pagination", "Yes")
            val response = api.getReviews(page = position, movieID = movieId )
            val nextKey = position + 1

            val result = response.body()?.toReview()
            if (position > (result?.total_pages ?: 1)) {
                return LoadResult.Error(Exception())
            }
            delay(200)
            return LoadResult.Page(
                data = result?.results ?: emptyList(),
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
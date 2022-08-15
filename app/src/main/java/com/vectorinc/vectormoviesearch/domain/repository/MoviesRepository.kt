package com.vectorinc.vectormoviesearch.domain.repository

import androidx.paging.PagingData
import com.vectorinc.vectormoviesearch.domain.model.*
import com.vectorinc.vectormoviesearch.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesGenre(
        fetchFromRemote: Boolean
    ): Flow<Resource<MoviesGenreListing>>


    suspend fun getMoviesTrendingPaging(page: Int): Flow<PagingData<Result>>


    suspend fun getSearchMovies(
        searchQuery: String, page: Int
    ): Flow<Resource<MoviesGenreListing>>

    suspend fun getMovieSelected(movieId: Int): Flow<Resource<MoviesDiscover>>

    suspend fun getMoviesTrending(fetchFromRemote: Boolean): Flow<Resource<MoviesGenreListing>>

    suspend fun getMoviesCredit(movieId: Int) : Flow<Resource<MovieCredit>>

    suspend fun getThumbNail(movieId: Int) : Flow<Resource<ThumbNail>>

    suspend fun getReview(movieId: Int) : Flow<Resource<Review>>


}
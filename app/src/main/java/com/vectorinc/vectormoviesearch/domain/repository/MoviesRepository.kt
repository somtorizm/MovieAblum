package com.vectorinc.vectormoviesearch.domain.repository

import androidx.paging.PagingData
import com.vectorinc.vectormoviesearch.domain.model.MoviesDiscover
import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesGenre(
        fetchFromRemote : Boolean
    ): Flow<Resource<MoviesGenreListing>>



    suspend fun getMoviesTrendingPaging(page: Int): Flow<PagingData<Result>>


    suspend fun getSearchMovies( searchQuery : String , page: Int
    ) : Flow<Resource<MoviesGenreListing>>

    suspend fun getMovieSelected(movieId : Int) : Flow<Resource<MoviesDiscover>>

    suspend fun getMoviesTrending(fetchFromRemote: Boolean): Flow<Resource<MoviesGenreListing>>
}
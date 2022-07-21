package com.vectorinc.vectormoviesearch.domain.repository

import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing
import com.vectorinc.vectormoviesearch.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesGenre(
        fetchFromRemote : Boolean
    ): Flow<Resource<MoviesGenreListing>>

    suspend fun getMoviesTrending(
        fetchFromRemote : Boolean
    ): Flow<Resource<MoviesGenreListing>>
}
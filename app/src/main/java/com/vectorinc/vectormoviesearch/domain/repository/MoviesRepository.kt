package com.vectorinc.vectormoviesearch.domain.repository

import androidx.paging.PagingData
import com.vectorinc.vectormoviesearch.domain.model.*
import com.vectorinc.vectormoviesearch.util.Resource
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getMoviesGenre(
       page: Int
    ): Flow<Resource<MoviesGenreListing>>




    suspend fun getSearchMovies(
        searchQuery: String, page: Int
    ): Flow<Resource<MoviesGenreListing>>

    suspend fun getMovieSelected(movieId: Int): Flow<Resource<MoviesDiscover>>

    suspend fun getMoviesTrending(page: Int): Flow<Resource<MoviesGenreListing>>



    suspend fun getMoviesCredit(movieId: Int): Flow<Resource<MovieCredit>>

    suspend fun getThumbNail(movieId: Int): Flow<Resource<ThumbNail>>

    suspend fun getReview(movieId: Int, page: Int): Flow<Resource<Review>>

    suspend fun getCastInfo(personId: Int): Flow<Resource<CastPerson>>

    suspend fun getCastImages(personId: Int): Flow<Resource<CastImages>>

    suspend fun getCastFeaturedMovie(personId: Int): Flow<Resource<ActorMoviesFeature>>


}
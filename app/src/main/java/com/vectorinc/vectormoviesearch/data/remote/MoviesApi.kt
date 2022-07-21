package com.vectorinc.vectormoviesearch.data.remote

import com.vectorinc.vectormoviesearch.data.remote.dto.MoviesGenreListingDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface MoviesApi {



    @GET("discover/movie?")
    suspend fun getGenreMoviesListing(
        @Query("api_key") apiKey : String = API_KEY
    ): MoviesGenreListingDto

    @GET("trending/all/day?")
    suspend fun getGenreTrendingListing(
        @Query("api_key") apiKey : String = API_KEY
    ): MoviesGenreListingDto

    companion object {
        const val API_KEY = "df3519c1f585ca1a3cfdf76d1b495fdb"
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}


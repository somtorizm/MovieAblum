package com.vectorinc.vectormoviesearch.data.remote

import com.vectorinc.vectormoviesearch.data.remote.dto.MoviesDiscoverDto
import com.vectorinc.vectormoviesearch.data.remote.dto.MoviesGenreListingDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {


    @GET("discover/movie?")
    suspend fun getGenreMoviesListing(
        @Query("api_key") apiKey: String = API_KEY
    ): MoviesGenreListingDto

    @GET("trending/all/day?")
    suspend fun getMoviesTrendingPaging(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int
    ): Response<MoviesGenreListingDto>

    @GET("trending/all/day?")
    suspend fun getMoviesTrending(
        @Query("api_key") apiKey: String = API_KEY,
    ): MoviesGenreListingDto

    @GET("search/movie?")
    suspend fun searchMoviesListing(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("query") searchQuery: String,
        @Query("page") page: Int
    ): Response<MoviesGenreListingDto>

    @GET("movie/{movie_id}?")
    suspend fun showMoviesSelected(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String = API_KEY,
    ): Response<MoviesDiscoverDto>


    companion object {
        const val API_KEY = "df3519c1f585ca1a3cfdf76d1b495fdb"
        const val BASE_URL = "https://api.themoviedb.org/3/"
    }
}


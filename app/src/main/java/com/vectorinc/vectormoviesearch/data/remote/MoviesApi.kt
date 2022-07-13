package com.vectorinc.vectormoviesearch.data.remote

import com.vectorinc.vectormoviesearch.data.remote.dto.MoviesGenreListingDto
import retrofit2.http.GET
import retrofit2.http.Headers

interface MoviesApi {


    @Headers(
        "X-RapidAPI-Key: 17ca74f629mshc70728372707709p13a566jsnf50b322dde8d",
        "X-RapidAPI-Host: advanced-movie-search.p.rapidapi.com"
    )
    @GET("movie?")
    suspend fun getGenreMoviesListing(

    ): MoviesGenreListingDto

    companion object {
        const val API_KEY = "17ca74f629mshc70728372707709p13a566jsnf50b322dde8d"
        const val API_HOST = "advanced-movie-search.p.rapidapi.com"
        const val BASE_URL = "https://advanced-movie-search.p.rapidapi.com/discover/"
    }
}


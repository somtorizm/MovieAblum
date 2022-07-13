package com.vectorinc.vectormoviesearch.data.remote.dto

import com.squareup.moshi.Json

data class MoviesGenreListingDto(
    @field: Json(name = "page") val page: Int?,
    @field : Json(name = "results") val result: List<Result>?,
    @field: Json(name = "total_pages") val totalPages: Int?,
    @field: Json(name = "total_results") val totalResults: Int?,

    )

data class Result(
    @field: Json(name = "adult") val adult: Boolean?,
    @field: Json(name = "backdrop_path") val backdropPath: String?,
    @field: Json(name = "id") val id: Int?,
    @field: Json(name = "original_language") val language: String?,
    @field: Json(name = "original_title") val titleOriginal: String?,
    @field: Json(name = "overview") val description: String?,
    @field: Json(name = "popularity") val popularity: Double?,
    @field: Json(name = "poster_path") val imagePoster: String?,
    @field: Json(name = "release_date") val dateReleased: String?,
    @field: Json(name = "title") val title: String?,
    @field: Json(name = "video") val hasVideo: Boolean?,
    @field: Json(name = "vote_average") val voteAverage: Double?,
    @field: Json(name = "vote_count") val voteCount: Int?,







    )

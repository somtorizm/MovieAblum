package com.vectorinc.vectormoviesearch.domain.model

data class MoviesGenreListing(
    val page: Int?,
    val result: List<Result>?,
    val totalPages: Int?,
    val totalResults: Int?,

    )

data class Result(
    val adult: Boolean?,
    val backdropPath: String?,
    val id: Int?,
    val language: String?,
    val titleOriginal: String?,
    val description: String?,
    val popularity: Double?,
    val imagePoster: String?,
    val dateReleased: String?,
    val title: String?,
    val hasVideo: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,


    )
package com.vectorinc.vectormoviesearch.domain.model

data class CastImages(
    val id: Int,
    val profiles: List<Profile>
)

data class Profile(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: Any?= null,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
)
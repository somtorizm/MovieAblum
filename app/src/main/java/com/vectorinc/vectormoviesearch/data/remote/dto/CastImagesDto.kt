package com.vectorinc.vectormoviesearch.data.remote.dto

data class CastImagesDto(
    val id: Int,
    val profiles: List<ProfileDto>
)

data class ProfileDto(
    val aspect_ratio: Double,
    val file_path: String,
    val height: Int,
    val iso_639_1: Any,
    val vote_average: Double,
    val vote_count: Int,
    val width: Int
)
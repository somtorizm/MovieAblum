package com.vectorinc.vectormoviesearch.data.remote.dto

data class CastDto(
    val adult: Boolean,
    val also_known_as: List<String>,
    val biography: String,
    val birthday: String? = null,
    val deathday: Any? = null,
    val gender: Int,
    val homepage: Any? =null,
    val id: Int,
    val imdb_id: String,
    val known_for_department: String,
    val name: String,
    val place_of_birth: String? =null,
    val popularity: Double,
    val profile_path: String? = null
)
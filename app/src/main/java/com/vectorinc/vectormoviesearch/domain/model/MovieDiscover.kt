package com.vectorinc.vectormoviesearch.domain.model

import com.vectorinc.vectormoviesearch.data.remote.dto.BelongsToCollection
import com.vectorinc.vectormoviesearch.data.remote.dto.Genre
import com.vectorinc.vectormoviesearch.data.remote.dto.ProductionCompany
import com.vectorinc.vectormoviesearch.data.remote.dto.ProductionCountry
import com.vectorinc.vectormoviesearch.data.remote.dto.SpokenLanguage


data class MoviesDiscover(
val adult: Boolean,
val backdrop_path: String? = null,
val belongs_to_collection: BelongsToCollection? = null,
val budget: Int,
val genres: List<Genre>,
val homepage: String? = null,
val id: Int,
val imdb_id: String?= null,
val original_language: String,
val original_title: String,
val overview: String? =null,
val popularity: Double,
val poster_path: String? =null,
val production_companies: List<ProductionCompany>,
val production_countries: List<ProductionCountry>,
val release_date: String,
val revenue: Int,
val runtime: Int?= null,
val spoken_languages: List<SpokenLanguage>,
val status: String,
val tagline: String? =null,
val title: String,
val video: Boolean,
val vote_average: Double,
val vote_count: Int
)

data class Genre(
    val id: Int,
    val name: String
)

data class BelongsToCollection(
    val backdrop_path: String,
    val id: Int,
    val name: String,
    val poster_path: String
)

data class ProductionCompany(
    val id: Int,
    val logo_path: String? =null,
    val name: String,
    val origin_country: String
)

data class ProductionCountry(
    val iso_3166_1: String,
    val name: String
)

data class SpokenLanguage(
    val english_name: String,
    val iso_639_1: String,
    val name: String
)
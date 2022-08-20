package com.vectorinc.vectormoviesearch.domain.model

data class ActorMoviesFeature(
    val cast: List<ActorsCast>,
    val crew: List<ActorsCrew>,
    val id: Int
)

data class ActorsCast(
    val adult: Boolean,
    val backdrop_path: String?= null,
    val character: String,
    val credit_id: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double?=null,
    val poster_path: String?= null,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double?=null,
    val vote_count: Int?=null
)


data class ActorsCrew(
    val adult: Boolean,
    val backdrop_path: String?= null,
    val credit_id: String,
    val department: String,
    val genre_ids: List<Int>,
    val id: Int,
    val job: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double?=null,
    val poster_path: String?= null,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double?=null,
    val vote_count: Int?=null
)
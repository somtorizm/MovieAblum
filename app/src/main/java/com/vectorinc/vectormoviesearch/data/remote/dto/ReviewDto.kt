package com.vectorinc.vectormoviesearch.data.remote.dto

data class ReviewDto(
    val id: Int,
    val page: Int,
    val results: List<ResultReview>,
    val total_pages: Int,
    val total_results: Int
)

data class ResultReview(
    val author: String,
    val author_details: AuthorDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)

data class AuthorDetails(
    val avatar_path: String,
    val name: String,
    val rating: Any,
    val username: String
)
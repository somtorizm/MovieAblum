package com.vectorinc.vectormoviesearch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MoviesTrendingEntity(
     val page: Int?,
    val result: List<Result>?,
    val totalPages: Int?,
    val totalResults: Int?,
     @PrimaryKey val _id : Int? = null
    )


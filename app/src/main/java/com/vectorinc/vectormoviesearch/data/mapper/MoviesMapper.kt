package com.vectorinc.vectormoviesearch.data.mapper


import com.vectorinc.vectormoviesearch.data.local.MoviesEntity
import com.vectorinc.vectormoviesearch.data.remote.dto.MoviesGenreListingDto
import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing
import com.vectorinc.vectormoviesearch.domain.model.Result


fun MoviesGenreListingDto.toMoviesEntity() : MoviesEntity{
    return MoviesEntity(
        page = page,
        result = result?.map {
            com.vectorinc.vectormoviesearch.data.local.Result(
                it.adult,
                it.backdropPath,
                it.id,
                it.language,
                it.titleOriginal,
                it.description,
                it.popularity,
                it.imagePoster,
                it.dateReleased,
                it.title,
                it.hasVideo,
                it.voteAverage,
                it.voteCount
            )
        },
        totalPages, totalResults
    )
}

fun MoviesEntity.toMoviesGenreListing(): MoviesGenreListing {
    return MoviesGenreListing(
        page = page,
        result = result?.map {
            com.vectorinc.vectormoviesearch.domain.model.Result(
                it.adult,
                it.backdropPath,
                it.id,
                it.language,
                it.titleOriginal,
                it.description,
                it.popularity,
                it.imagePoster,
                it.dateReleased,
                it.title,
                it.hasVideo,
                it.voteAverage,
                it.voteCount
            )
        },
        totalPages, totalResults
    )
}


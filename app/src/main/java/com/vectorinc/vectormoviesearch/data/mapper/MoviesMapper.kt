package com.vectorinc.vectormoviesearch.data.mapper


import com.vectorinc.vectormoviesearch.data.local.MoviesEntity
import com.vectorinc.vectormoviesearch.data.local.MoviesTrendingEntity
import com.vectorinc.vectormoviesearch.data.remote.dto.MovieCreditDto
import com.vectorinc.vectormoviesearch.data.remote.dto.MoviesDiscoverDto
import com.vectorinc.vectormoviesearch.data.remote.dto.MoviesGenreListingDto
import com.vectorinc.vectormoviesearch.domain.model.MovieCredit
import com.vectorinc.vectormoviesearch.domain.model.MoviesDiscover
import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing
import com.vectorinc.vectormoviesearch.domain.model.Result


fun MoviesGenreListingDto.toMoviesEntity(): MoviesEntity {
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

fun MoviesGenreListingDto.toMoviesGenreListing(): MoviesGenreListing {
    return MoviesGenreListing(
        page, result?.map {
            Result(
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
        } as MutableList<Result>?, totalPages, totalResults
    )
}
fun MoviesDiscoverDto.toMoviesDiscover() : MoviesDiscover{
    return MoviesDiscover(
        adult,backdrop_path ?: "", belongs_to_collection,budget,genres,homepage,
        id,imdb_id,original_language,original_title,overview,popularity,poster_path,production_companies,
        production_countries,release_date,revenue,runtime,spoken_languages,status,tagline,title,video,vote_average,vote_count
    )
}
fun MoviesGenreListingDto.toMoviesTrending(): MoviesTrendingEntity {
    return MoviesTrendingEntity(
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
        } as MutableList<Result>?,
        totalPages, totalResults
    )
}

fun MovieCreditDto.toMoviesCredit() : MovieCredit{
    return MovieCredit(
        cast = cast,
        crew = crew,
        id
    )
}







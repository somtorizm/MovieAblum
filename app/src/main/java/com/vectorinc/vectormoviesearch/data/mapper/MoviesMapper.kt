package com.vectorinc.vectormoviesearch.data.mapper


import com.vectorinc.vectormoviesearch.data.local.MoviesEntity
import com.vectorinc.vectormoviesearch.data.local.MoviesTrendingEntity
import com.vectorinc.vectormoviesearch.data.remote.dto.*
import com.vectorinc.vectormoviesearch.domain.model.*
import com.vectorinc.vectormoviesearch.domain.model.Cast
import com.vectorinc.vectormoviesearch.domain.model.Crew
import com.vectorinc.vectormoviesearch.domain.model.Result
import com.vectorinc.vectormoviesearch.domain.model.ResultReview
import com.vectorinc.vectormoviesearch.domain.model.ResultThumbNail


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

fun MoviesDiscoverDto.toMoviesDiscover(): MoviesDiscover {
    return MoviesDiscover(
        adult,
        backdrop_path ?: "",
        belongs_to_collection,
        budget,
        genres,
        homepage,
        id,
        imdb_id,
        original_language,
        original_title,
        overview,
        popularity,
        poster_path,
        production_companies,
        production_countries,
        release_date,
        revenue,
        runtime,
        spoken_languages,
        status,
        tagline,
        title,
        video,
        vote_average,
        vote_count
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

fun ThumbNailDto.toThumbNail(): ThumbNail {
    return ThumbNail(
        id, results.map {
            ResultThumbNail(
                it.id,
                it.iso_3166_1,
                it.iso_639_1,
                it.key,
                it.name,
                it.official,
                it.published_at,
                it.site,
                it.size,
                it.type
            )
        }
    )
}

fun MovieCreditDto.toMoviesCredit(): MovieCredit {
    return MovieCredit(
        cast = cast.map {
            Cast(
                it.adult,
                it.cast_id,
                it.character,
                it.credit_id,
                it.gender,
                it.id,
                it.known_for_department,
                it.name,
                it.order,
                it.original_name,
                it.popularity,
                it.profile_path
            )
        },
        crew = crew.map {
            Crew(
                it.adult,
                it.credit_id,
                it.department,
                it.gender,
                it.id,
                it.job,
                it.known_for_department,
                it.name,
                it.original_name,
                it.popularity,
                it.profile_path
            )
        },
        id
    )
}

fun ReviewDto.toReview(): Review {

    return Review(
        id, page, results.map {
            val authorDetail = AuthorDetail(
                it.author_details.avatar_path,
                it.author_details.name,
                it.author_details.rating,
                it.author_details.username
            )
            ResultReview(
                it.author,
                authorDetail,
                it.content,
                it.created_at,
                it.id,
                it.updated_at,
                it.url
            )
        }, total_pages, total_results
    )
}

fun CastDto.toCast(): CastPerson {
    return CastPerson(
        adult,
        also_known_as,
        biography,
        birthday,
        deathday,
        gender,
        homepage,
        id,
        imdb_id,
        known_for_department,
        name,
        place_of_birth,
        popularity,
        profile_path
    )
}

fun CastImagesDto.toCastImages(): CastImages {
    return CastImages(
        id, profiles.map {
            Profile(
                it.aspect_ratio,
                it.file_path,
                it.height,
                it.iso_639_1,
                it.vote_average,
                it.vote_count,
                it.width
            )
        }
    )
}

fun ActorMoviesFeatureDto.toActorMovie(): ActorMoviesFeature {
    return ActorMoviesFeature(
        cast.map {
            ActorsCast(
                it.adult,
                it.backdrop_path,
                it.character,
                it.credit_id,
                it.genre_ids,
                it.id,
                it.original_language,
                it.original_title,
                it.overview,
                it.popularity,
                it.poster_path,
                it.release_date,
                it.title,
                it.video,
                it.vote_average,
                it.vote_count
            )
        },
        crew.map {
            ActorsCrew(
                it.adult,
                it.backdrop_path,
                it.credit_id,
                it.department,
                it.genre_ids,
                it.id,
                it.job,
                it.original_language,
                it.original_title,
                it.overview,
                it.popularity,
                it.poster_path,
                it.release_date,
                it.title,
                it.video,
                it.vote_average,
                it.vote_count
            )
        }, id
    )
}






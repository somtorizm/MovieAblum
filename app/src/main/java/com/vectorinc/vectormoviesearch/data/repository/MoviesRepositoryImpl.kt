package com.vectorinc.vectormoviesearch.data.repository

import com.vectorinc.vectormoviesearch.data.local.MovieDatabase
import com.vectorinc.vectormoviesearch.data.mapper.toMoviesEntity
import com.vectorinc.vectormoviesearch.data.mapper.toMoviesGenreListing
import com.vectorinc.vectormoviesearch.data.mapper.toMoviesTrending
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.domain.model.MoviesGenreListing
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import com.vectorinc.vectormoviesearch.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesApi,
    private val db: MovieDatabase
) : MoviesRepository {
    private val dao = db.dao

    override suspend fun getMoviesGenre(fetchFromRemote: Boolean): Flow<Resource<MoviesGenreListing>> {
        return flow {
            emit(Resource.isLoading(true))
            val localMoviesListings = dao.readMovieListings()
            if (localMoviesListings != null) {
                emit(
                    Resource.Success(
                        data = localMoviesListings.toMoviesGenreListing()
                    )
                )
            }
            val shouldLoadFromCache = (localMoviesListings != null && !fetchFromRemote)

            if (shouldLoadFromCache) {
                emit(Resource.isLoading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getGenreMoviesListing()
                response.toMoviesEntity()

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListings?.let { listings ->
                dao.clearMovieListings()
                dao.insertMovieListings(listings)
                emit(
                    Resource.Success(
                        data = dao.readMovieListings().toMoviesGenreListing()
                    )
                )
                emit(Resource.isLoading(false))


            }
        }

    }

    override suspend fun getMoviesTrending(fetchFromRemote: Boolean): Flow<Resource<MoviesGenreListing>> {
        return flow {
            emit(Resource.isLoading(true))
            val localMoviesListings = dao.readMovieTrendingListings()
            if (localMoviesListings != null) {
                emit(
                    Resource.Success(
                        data = localMoviesListings.toMoviesGenreListing()
                    )
                )
            }
            val shouldLoadFromCache = (localMoviesListings != null && !fetchFromRemote)

            if (shouldLoadFromCache) {
                emit(Resource.isLoading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getGenreTrendingListing()
                response.toMoviesTrending()

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteListings?.let { listings ->
                dao.insertTrendingListings(listings)
                emit(
                    Resource.Success(
                        data = dao.readMovieTrendingListings().toMoviesGenreListing()
                    )
                )
                emit(Resource.isLoading(false))


            }
        }

    }

}
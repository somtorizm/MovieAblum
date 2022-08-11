package com.vectorinc.vectormoviesearch.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vectorinc.vectormoviesearch.data.local.MovieDatabase
import com.vectorinc.vectormoviesearch.data.mapper.*
import com.vectorinc.vectormoviesearch.data.remote.MoviesApi
import com.vectorinc.vectormoviesearch.data.pagination.MoviesPagingSource
import com.vectorinc.vectormoviesearch.data.pagination.TrendingMoviesPagingSource
import com.vectorinc.vectormoviesearch.domain.model.*
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

    override suspend fun getMoviesTrendingPaging(page: Int): Flow<PagingData<Result>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TrendingMoviesPagingSource(api) }
        ).flow
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
                val response = api.getMoviesTrending()
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

    override suspend fun getMoviesCredit(movieId: Int): Flow<Resource<MovieCredit>> {
        return flow {

            val remoteMovies = try {
                api.getMovieCredits(movieId)


            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                Log.d("Hello", "http erro loading")

                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteMovies.let {
                val data = it?.body()?.toMoviesCredit()
                emit(Resource.Success(data))

            }


        }
    }

    override suspend fun getThumbNail(movieId: Int): Flow<Resource<ThumbNail>> {
        return flow {

            val remoteMovies = try {
                api.getThumbNails(movieId)


            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                Log.d("Hello", "http error loading")

                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteMovies.let {
                val data = it?.body()?.toThumbNail()
                emit(Resource.Success(data))

            }


        }

    }


    override suspend fun getSearchMovies(
        searchQuery: String,
        page: Int
    ): Flow<Resource<MoviesGenreListing>> {
        return flow {
            emit(Resource.isLoading(true))

            val remoteListings = try {
                val response = api.searchMoviesListing(searchQuery = searchQuery, page = page)
                emit(
                    Resource.Success(
                        data = response.body()?.toMoviesGenreListing()
                    )
                )
                emit(Resource.isLoading(false))

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.isLoading(false))

                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                emit(Resource.isLoading(false))

                null
            }


        }
    }

    override suspend fun getMovieSelected(movieId: Int): Flow<Resource<MoviesDiscover>> {
        return flow {

            val remoteMovies = try {
                api.showMoviesSelected(movieId)


            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                Log.d("Hello", "http erro loading")

                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            remoteMovies.let {
                val data = it?.body()?.toMoviesDiscover()
                emit(Resource.Success(data))

            }


        }

    }



}
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
) : MoviesRepository {

    override suspend fun getMoviesGenre(page: Int): Flow<Resource<MoviesGenreListing>> {
        return flow {

            val remoteMovies = try {
                api.getGenreMoviesListing(page = page)


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
                val data = it?.body()?.toMoviesGenreListing()
                emit(Resource.Success(data))

            }


        }


    }



    override suspend fun getMoviesTrending(page: Int): Flow<Resource<MoviesGenreListing>> {
        return flow {

            val remoteMovies = try {
                api.getMoviesTrendingPaging(page = page)


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
                val data = it?.body()?.toMoviesGenreListing()
                emit(Resource.Success(data))

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
                Log.d("Hello", "http error loading")

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

    override suspend fun getReview(movieId: Int, page: Int): Flow<Resource<Review>> {
        return flow {

            val remoteMovies = try {
                api.getReviews(movieId,page)


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
                val data = it?.body()?.toReview()
                emit(Resource.Success(data))

            }


        }

    }

    override suspend fun getCastInfo(personId: Int): Flow<Resource<CastPerson>> {
        return flow {

            val remoteMovies = try {
                api.getCastInfo(personId)


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
                val data = it?.body()?.toCast()
                emit(Resource.Success(data))

            }


        }

    }

    override suspend fun getCastImages(personId: Int): Flow<Resource<CastImages>> {
        return flow {

            val remoteMovies = try {
                api.getCastImages(personId)


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
                val data = it?.body()?.toCastImages()
                emit(Resource.Success(data))

            }


        }
    }

    override suspend fun getCastFeaturedMovie(personId: Int): Flow<Resource<ActorMoviesFeature>> {
        return flow {

            val remoteMovies = try {
                api.getActorFeaturedMovies(personId)


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
                val data = it?.body()?.toActorMovie()
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
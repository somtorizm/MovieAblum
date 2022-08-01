package com.vectorinc.vectormoviesearch.di

import com.vectorinc.vectormoviesearch.data.repository.MoviesRepositoryImpl
import com.vectorinc.vectormoviesearch.domain.repository.MoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieRepository(
        moviesRepository: MoviesRepositoryImpl
    ): MoviesRepository


}
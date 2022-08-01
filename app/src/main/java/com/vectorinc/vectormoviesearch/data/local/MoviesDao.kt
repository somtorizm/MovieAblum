package com.vectorinc.vectormoviesearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieListings(
        moviesEntity: MoviesEntity
    )

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrendingListings(
        moviesTrendingEntity: MoviesTrendingEntity
    )

    @Query("DELETE FROM moviesEntity")
    suspend fun clearMovieListings()

    @Query("DELETE FROM moviestrendingentity")
    suspend fun clearMovieTrendingListings()

    @Query(
        """
          SELECT * FROM  MoviesEntity
        """
    )
    suspend fun readMovieListings(): MoviesEntity


    @Query(
        """
           SELECT *FROM  moviestrendingentity
         """
    )
    suspend fun readMovieTrendingListings(): MoviesEntity

}
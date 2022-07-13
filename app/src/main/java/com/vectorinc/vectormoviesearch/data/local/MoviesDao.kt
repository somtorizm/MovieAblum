package com.vectorinc.vectormoviesearch.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        moviesEntity: MoviesEntity
    )

    @Query("DELETE FROM moviesEntity")
    suspend fun clearMovieListings()

    @Query(
        """
        SELECT * 
      FROM  MoviesEntity
    """
    )
    suspend fun readCompanyListings(): MoviesEntity


}
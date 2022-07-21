package com.vectorinc.vectormoviesearch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@TypeConverters(value = [Converter::class])
@Database(
    entities = [MoviesEntity::class, MoviesTrendingEntity::class], version = 1,
    )
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MoviesDao
}
package com.android.sample.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.sample.core.BuildConfig
import com.android.sample.core.database.movie.DbMovieConverter
import com.android.sample.core.database.movie.DbMovies
import com.android.sample.core.database.movie.MovieDao
import com.android.sample.core.database.detail.DbMovieDetail
import com.android.sample.core.database.detail.MovieDetailDao

@Database(
    entities = [DbMovies::class, DbMovieDetail::class],
    version = BuildConfig.DATABASE_VERSION,
    exportSchema = BuildConfig.DATABASE_EXPORT_SCHEMA
)
@TypeConverters(DbMovieConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun movieDetailDao(): MovieDetailDao
}
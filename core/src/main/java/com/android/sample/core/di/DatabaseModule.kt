package com.android.sample.core.di

import android.content.Context
import androidx.room.Room
import com.android.sample.core.BuildConfig
import com.android.sample.core.database.AppDatabase
import com.android.sample.core.database.movie.DbMovieConverter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideMyDatabase(context: Context, moshi: Moshi): AppDatabase {
        DbMovieConverter.initialize(moshi)
        return Room.databaseBuilder(context,
            AppDatabase::class.java,
            BuildConfig.DATABASE_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideMovieDetailDao(db: AppDatabase) = db.movieDetailDao()
}
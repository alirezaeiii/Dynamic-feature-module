package com.android.sample.core.di

import com.android.sample.common.base.BaseRepository
import com.android.sample.core.repository.MovieDetailRepository
import com.android.sample.core.repository.MovieRepository
import com.android.sample.core.response.MovieDetail
import com.android.sample.core.response.Movies
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun bindMovieRepository(movieRepository: MovieRepository): BaseRepository<Movies>

    @Singleton
    @Binds
    internal abstract fun bindMovieDetailRepository(movieDetailRepository: MovieDetailRepository): BaseRepository<MovieDetail>
}
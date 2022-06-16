package com.android.sample.core.di

import android.content.Context
import com.android.sample.common.base.BaseRepository
import com.android.sample.common.di.UtilsModule
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.database.detail.MovieDetailDao
import com.android.sample.core.database.movie.MovieDao
import com.android.sample.core.network.ApiService
import com.android.sample.core.response.MovieDetail
import com.android.sample.core.response.Movies
import dagger.Component
import javax.inject.Singleton

/**
 * Core component that all module's components depend on.
 *
 * @see Component
 */
@Singleton
@Component(
    modules = [
        ContextModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        UtilsModule::class,
        RepositoryModule::class]
)
interface CoreComponent {

    /**
     * Provide dependency graph Context
     *
     * @return Context
     */
    fun context(): Context

    /**
     * Provide dependency graph apiService
     *
     * @return MarvelService
     */
    fun apiService(): ApiService

    /**
     * Provide dependency graph DashboardDao
     *
     * @return MovieDao
     */
    fun movieDao(): MovieDao

    /**
     * Provide dependency graph SectionDao
     *
     * @return MovieDetailDao
     */
    fun movieDetailDao(): MovieDetailDao

    /**
     * Provide dependency graph SchedulerProvider
     *
     * @return BaseSchedulerProvider
     */
    fun schedulerProvider(): BaseSchedulerProvider

    /**
     * Provide dependency graph DashboardRepository
     *
     * @return BaseRepository
     */
    fun movieRepository(): BaseRepository<Movies>

    /**
     * Provide dependency graph SectionRepository
     *
     * @return BaseRepository
     */
    fun movieDetailRepository(): BaseRepository<MovieDetail>
}
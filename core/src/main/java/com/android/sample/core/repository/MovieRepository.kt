package com.android.sample.core.repository

import com.android.sample.common.base.BaseRepository
import com.android.sample.core.database.movie.MovieDao
import com.android.sample.core.database.movie.asDomainModel
import com.android.sample.core.network.ApiService
import com.android.sample.core.response.Movies
import com.android.sample.core.response.asDatabaseModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val remoteDataSource: ApiService,
    private val dao: MovieDao
) : BaseRepository<Movies>() {

    override fun getResultFromRemote(id: Int?): Observable<Movies> =
        remoteDataSource.getMovies().flatMap {
            dao.insert(it.asDatabaseModel()).andThen(Observable.fromCallable { it })
        }

    override fun getResultFromLocal(id: Int?): Movies? =
        dao.getMovies()?.asDomainModel()
}
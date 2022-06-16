package com.android.sample.core.repository

import com.android.sample.common.base.BaseRepository
import com.android.sample.core.database.detail.MovieDetailDao
import com.android.sample.core.database.detail.asDomainModel
import com.android.sample.core.network.ApiService
import com.android.sample.core.response.MovieDetail
import com.android.sample.core.response.asDatabaseModel
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailRepository @Inject constructor(
    private val remoteDataSource: ApiService,
    private val dao: MovieDetailDao,
) : BaseRepository<MovieDetail>() {

    override fun getResultFromRemote(id: Int?): Observable<MovieDetail> =
        remoteDataSource.getMovieData().map { it.movieData.find {
                movieDetail ->  movieDetail.id == id } }.flatMap {
            dao.insert(it.asDatabaseModel())
                .andThen(Observable.fromCallable { it })
        }

    override fun getResultFromLocal(id: Int?): MovieDetail? =
        dao.getMovieDetail(id!!)?.asDomainModel()
}
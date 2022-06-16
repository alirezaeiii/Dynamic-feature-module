package com.android.sample.common.base

import com.android.sample.common.util.NoDataException
import io.reactivex.Observable

abstract class BaseRepository<T> {

    private var cacheIsDirty = false

    protected abstract fun getResultFromRemote(id: Int?): Observable<T>

    protected abstract fun getResultFromLocal(id: Int?): T?

    fun getResult(id: Int?): Observable<T> =
        Observable.fromCallable { cacheIsDirty }.flatMap {
            if (it) {
                getResultFromRemote(id)
                    .doOnComplete { cacheIsDirty = false }
            } else {
                val resultFromLocalDataSource = getResultFromLocal(id)
                Observable.create { subscriber ->
                    if (resultFromLocalDataSource == null) {
                        subscriber.onError(NoDataException())
                    } else {
                        subscriber.onNext(resultFromLocalDataSource)
                    }
                }
            }.onErrorResumeNext(getResultFromRemote(id))
        }

    fun refresh() {
        cacheIsDirty = true
    }
}
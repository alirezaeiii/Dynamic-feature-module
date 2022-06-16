package com.android.sample.core.database.detail

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

@Dao
interface MovieDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DbMovieDetail): Completable

    @Query("SELECT * FROM details WHERE id=:id")
    fun getMovieDetail(id: Int): DbMovieDetail?
}
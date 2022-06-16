package com.android.sample.core.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: DbMovies): Completable

    @Query("SELECT * FROM movies")
    fun getMovies(): DbMovies?
}
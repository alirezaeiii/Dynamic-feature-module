package com.android.sample.core.database.movie

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

object DbMovieConverter {

    private lateinit var dbMovieJsonAdapter: JsonAdapter<List<DbMovie>>

    fun initialize(moshi: Moshi){
        val listDbLink = Types.newParameterizedType(MutableList::class.java, DbMovie::class.java)
        dbMovieJsonAdapter = moshi.adapter(listDbLink)
    }

    @TypeConverter
    @JvmStatic
    fun jsonToList(value: String): List<DbMovie>? = dbMovieJsonAdapter.fromJson(value)

    @TypeConverter
    @JvmStatic
    fun listToJson(list: List<DbMovie>?): String = dbMovieJsonAdapter.toJson(list)
}
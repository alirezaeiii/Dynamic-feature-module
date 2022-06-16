package com.android.sample.core.response

import com.android.sample.core.database.detail.DbMovieDetail
import com.squareup.moshi.Json

class MovieDetail(
    @Json(name = "movie_id") val id: Int,
    val title: String,
    @Json(name = "sub_title") val subTitle: String
)

fun MovieDetail.asDatabaseModel(): DbMovieDetail = DbMovieDetail(
    id = this.id, title = this.title, subTitle = this.subTitle
)
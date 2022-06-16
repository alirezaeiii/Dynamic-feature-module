package com.android.sample.core.database.detail

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.sample.core.response.MovieDetail

@Entity(tableName = "details")
class DbMovieDetail(
    @PrimaryKey val id: Int,
    val title: String,
    val subTitle: String
)

fun DbMovieDetail.asDomainModel(): MovieDetail = MovieDetail(
    id = this.id, title = this.title, subTitle = this.subTitle
)
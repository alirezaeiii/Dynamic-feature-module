package com.android.sample.core.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    @Json(name = "movie_id") val id: Int,
    val price: String,
    val image: String,
    val available: Boolean
) : Parcelable
package com.android.sample.core.response

import com.squareup.moshi.Json

class Detail(
    @Json(name = "movie_data") val movieData: List<MovieDetail>,
)
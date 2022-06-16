package com.android.sample.core.response

import com.android.sample.core.database.movie.DbMovies
import com.android.sample.core.database.movie.DbMovie
import com.squareup.moshi.Json

class Movies(
    @Json(name = "image_base") val imageBase: String,
    @Json(name = "movie_offers") val movies: List<Movie>
)

fun Movies.asDatabaseModel(): DbMovies = DbMovies(
    imageBase = this.imageBase,
    movies = this.movies.map {
        DbMovie(id = it.id, price = it.price, image = it.image, available = it.available)
    }
)
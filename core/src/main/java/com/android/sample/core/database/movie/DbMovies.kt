package com.android.sample.core.database.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.sample.core.response.Movies
import com.android.sample.core.response.Movie

@Entity(tableName = "movies")
class DbMovies(
    @PrimaryKey val primaryKey: String = "movie",
    val imageBase: String,
    val movies: List<DbMovie>
)

fun DbMovies.asDomainModel(): Movies = Movies(
    imageBase = this.imageBase,
    movies = this.movies.map {
        Movie(id = it.id, price = it.price, image = it.image, available = it.available)
    })


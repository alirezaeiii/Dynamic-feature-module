package com.android.sample.core.network

import com.android.sample.core.response.Detail
import com.android.sample.core.response.Movies
import io.reactivex.Observable
import retrofit2.http.GET

interface ApiService {

    @GET("movie_offers.json")
    fun getMovies(): Observable<Movies>

    @GET("movie_data.json")
    fun getMovieData(): Observable<Detail>
}
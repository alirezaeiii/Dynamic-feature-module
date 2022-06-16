package com.android.sample.feature.list.viewmodel

import com.android.sample.common.base.BaseRepository
import com.android.sample.common.base.BaseViewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Movie
import com.android.sample.core.response.MovieDetail
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    repository: BaseRepository<MovieDetail>,
    schedulerProvider: BaseSchedulerProvider,
    movie: Movie
) : BaseViewModel<MovieDetail>(repository, schedulerProvider, movie.id)
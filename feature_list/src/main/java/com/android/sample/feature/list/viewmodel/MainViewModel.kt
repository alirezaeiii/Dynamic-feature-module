package com.android.sample.feature.list.viewmodel

import com.android.sample.common.base.BaseRepository
import com.android.sample.common.base.BaseViewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Movies
import javax.inject.Inject

class MainViewModel @Inject constructor(
    repository: BaseRepository<Movies>,
    schedulerProvider: BaseSchedulerProvider,
) : BaseViewModel<Movies>(repository, schedulerProvider)
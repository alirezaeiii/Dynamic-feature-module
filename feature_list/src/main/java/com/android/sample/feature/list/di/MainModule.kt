package com.android.sample.feature.list.di

import com.android.sample.common.base.BaseRepository
import com.android.sample.common.extension.viewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Movies
import com.android.sample.feature.list.ui.MainFragment
import com.android.sample.feature.list.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainModule(private val fragment: MainFragment) {

    @Provides
    fun providesMainViewModel(
        repository: BaseRepository<Movies>,
        schedulerProvider: BaseSchedulerProvider
    ) = fragment.viewModel {
        MainViewModel(repository, schedulerProvider)
    }
}
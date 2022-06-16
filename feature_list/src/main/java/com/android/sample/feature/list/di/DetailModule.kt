package com.android.sample.feature.list.di

import androidx.navigation.fragment.navArgs
import com.android.sample.common.base.BaseRepository
import com.android.sample.common.extension.viewModel
import com.android.sample.common.util.schedulers.BaseSchedulerProvider
import com.android.sample.core.response.Movie
import com.android.sample.core.response.MovieDetail
import com.android.sample.feature.list.ui.DetailFragment
import com.android.sample.feature.list.ui.DetailFragmentArgs
import com.android.sample.feature.list.viewmodel.DetailViewModel
import dagger.Module
import dagger.Provides

@Module
class DetailModule(private val fragment: DetailFragment) {

    @Provides
    fun provideDetailViewModel(
        repository: BaseRepository<MovieDetail>,
        schedulerProvider: BaseSchedulerProvider,
        movie: Movie
    ) = fragment.viewModel {
        DetailViewModel(repository, schedulerProvider, movie)
    }

    @Provides
    internal fun provideMovie(): Movie {
        val args: DetailFragmentArgs by fragment.navArgs()
        return args.movie
    }
}
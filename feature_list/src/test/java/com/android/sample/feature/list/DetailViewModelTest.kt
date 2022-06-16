package com.android.sample.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.common.util.ViewState
import com.android.sample.common.util.schedulers.TestSchedulerProvider
import com.android.sample.core.database.detail.DbMovieDetail
import com.android.sample.core.database.detail.MovieDetailDao
import com.android.sample.core.network.ApiService
import com.android.sample.core.repository.MovieDetailRepository
import com.android.sample.core.response.Movie
import com.android.sample.feature.list.viewmodel.DetailViewModel
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: ApiService

    @Mock
    private lateinit var dao: MovieDetailDao

    private lateinit var schedulerProvider: TestSchedulerProvider
    private lateinit var movieDetailRepository: MovieDetailRepository
    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        // Make the sure that all schedulers are immediate.
        schedulerProvider = TestSchedulerProvider()
        movieDetailRepository = MovieDetailRepository(service, dao)

        val movie = Movie(1, "32.00€", "imageUrl", false)
        viewModel = DetailViewModel(movieDetailRepository, schedulerProvider, movie)
    }

    @Test
    fun givenDaoReturnCache_whenGetResult_thenReturnSuccessFromCache() {
        val movieDetail = DbMovieDetail(1, "title", "subTitle")
        `when`(dao.getMovieDetail(anyInt())).thenReturn(movieDetail)
        `when`(service.getMovieData()).thenReturn(Observable.error(Exception("")))

        viewModel.liveData.value.let {
            assertThat(it, `is`(ViewState.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is ViewState.Success) {
                it.data?.let { data ->
                    assertTrue(data.id == 1)
                    assertTrue(data.title == "title")
                    assertTrue(data.subTitle == "subTitle")
                }
            } else {
                fail("Wrong type $it")
            }
        }
    }

    @Test
    fun givenDaoReturnNull_whenGetResult_thenReturnErrorWithNullMessage() {
        `when`(dao.getMovieDetail(anyInt())).thenReturn(null)

        viewModel.liveData.value.let {
            assertThat(it, `is`(ViewState.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is ViewState.Error) {
                assertThat(it.message, `is`(nullValue()))
            } else {
                fail("Wrong type $it")
            }
        }
    }

    @Test
    fun givenServerReturnError_whenGetResult_thenReturnErrorWithMessage() {
        `when`(service.getMovieData()).thenReturn(Observable.error(Exception("error")))

        viewModel.liveData.value.let {
            assertThat(it, `is`(ViewState.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is ViewState.Error) {
                assertThat(it.message, `is`(notNullValue()))
                assertThat(it.message, `is`("error"))
            } else {
                fail("Wrong type $it")
            }
        }
    }
}
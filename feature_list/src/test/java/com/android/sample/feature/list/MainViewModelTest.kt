package com.android.sample.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.sample.common.util.ViewState
import com.android.sample.common.util.schedulers.TestSchedulerProvider
import com.android.sample.core.database.movie.MovieDao
import com.android.sample.core.network.ApiService
import com.android.sample.core.repository.MovieRepository
import com.android.sample.core.response.Movies
import com.android.sample.core.response.asDatabaseModel
import com.android.sample.feature.list.viewmodel.MainViewModel
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.TimeUnit

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var service: ApiService

    @Mock
    private lateinit var dao: MovieDao

    private lateinit var schedulerProvider: TestSchedulerProvider
    private lateinit var movieRepository: MovieRepository
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        // Make the sure that all schedulers are immediate.
        schedulerProvider = TestSchedulerProvider()

        movieRepository = MovieRepository(service, dao)
        viewModel = MainViewModel(movieRepository, schedulerProvider)
    }

    @Test
    fun givenDaoReturnCache_whenGetResult_thenReturnSuccessFromCache() {
        val movies = Movies(imageBase = "", movies = emptyList())
        `when`(dao.getMovies()).thenReturn(movies.asDatabaseModel())
        `when`(service.getMovies()).thenReturn(Observable.error(Exception("")))

        viewModel.liveData.value.let {
            assertThat(it, `is`(ViewState.Loading))
        }

        schedulerProvider.testScheduler.advanceTimeBy(1, TimeUnit.MILLISECONDS)

        viewModel.liveData.value.let {
            assertThat(it, `is`(notNullValue()))
            if (it is ViewState.Success) {
                it.data?.let { data ->
                    assertTrue(data.movies.isEmpty())
                }
            } else {
                fail("Wrong type $it")
            }
        }
    }

    @Test
    fun givenDaoReturnNull_whenGetResult_thenReturnErrorWithNullMessage() {
        `when`(dao.getMovies()).thenReturn(null)

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
        `when`(service.getMovies()).thenReturn(Observable.error(Exception("error")))

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
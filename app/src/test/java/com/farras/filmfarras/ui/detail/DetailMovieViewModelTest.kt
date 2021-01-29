package com.farras.filmfarras.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.utils.DataDummy
import com.farras.filmfarras.utils.LiveDataTestUtil
import com.farras.filmfarras.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailMovieViewModelTest {

    private lateinit var viewModel: DetailMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var cinemaRepository: CinemaRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<MovieEntity>>

    @Mock
    private lateinit var actorsObserver: Observer<Resource<List<ActorEntity>>>

    @Before
    fun setUp() {
        viewModel = DetailMovieViewModel(cinemaRepository)
    }

    @Test
    fun getMovie() {
        val movieId = DataDummy.generateDummyMovies()[0].id
        viewModel.setSelectedMovie(movieId)
        val dummyMovie = Resource.success(DataDummy.generateDummyMovie(movieId))
        val movie = MutableLiveData<Resource<MovieEntity>>()
        movie.value = dummyMovie

        `when`(cinemaRepository.getMovieDetail(movieId)).thenReturn(movie)

        viewModel.movieResource.observeForever(movieObserver)
        verify(movieObserver).onChanged(dummyMovie)
    }

    @Test
    fun getActorList() {
        val movieId = DataDummy.generateDummyMovies()[0].id
        viewModel.setSelectedMovie(movieId)
        val dummyMovieActors = Resource.success(DataDummy.generateDummyMovieActors(movieId))
        val movieActors = MutableLiveData<Resource<List<ActorEntity>>>()
        movieActors.value = dummyMovieActors

        `when`(cinemaRepository.getMovieActorList(movieId)).thenReturn(movieActors)
        val actorEntities = LiveDataTestUtil.getValue(viewModel.getMovieActorList())
        verify(cinemaRepository).getMovieActorList(movieId)
        assertNotNull(actorEntities)
        assertEquals(dummyMovieActors.data?.size, actorEntities.data?.size)

        viewModel.getMovieActorList().observeForever(actorsObserver)
        verify(actorsObserver).onChanged(dummyMovieActors)
    }
}
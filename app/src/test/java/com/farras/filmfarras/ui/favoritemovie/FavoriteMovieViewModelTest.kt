package com.farras.filmfarras.ui.favoritemovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
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
class FavoriteMovieViewModelTest {

    private lateinit var viewModel: FavoriteMovieViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var cinemaRepository: CinemaRepository

    @Mock
    private lateinit var observer: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteMovieViewModel(cinemaRepository)
    }

    @Test
    fun getFavoriteMovies() {
        val dummyMovies = pagedList
        `when`(dummyMovies.size).thenReturn(20)
        val movies = MutableLiveData<PagedList<MovieEntity>>()
        movies.value = dummyMovies

        `when`(cinemaRepository.getFavoriteMovieList()).thenReturn(movies)
        val movieEntities = viewModel.getFavoriteMovies().value
        verify(cinemaRepository).getFavoriteMovieList()
        assertNotNull(movieEntities)
        assertEquals(20, movieEntities?.size)

        viewModel.getFavoriteMovies().observeForever(observer)
        verify(observer).onChanged(dummyMovies)
    }
}
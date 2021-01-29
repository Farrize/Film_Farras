package com.farras.filmfarras.ui.favoritetvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteTvShowViewModelTest {

    private lateinit var viewModel: FavoriteTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var cinemaRepository: CinemaRepository

    @Mock
    private lateinit var observer: Observer<PagedList<TvShowEntity>>

    @Mock
    private lateinit var pagedList: PagedList<TvShowEntity>

    @Before
    fun setUp() {
        viewModel = FavoriteTvShowViewModel(cinemaRepository)
    }

    @Test
    fun getFavoriteTvShows() {
        val dummyTvShows = pagedList
        `when`(dummyTvShows.size).thenReturn(20)
        val tvShows = MutableLiveData<PagedList<TvShowEntity>>()
        tvShows.value = dummyTvShows

        `when`(cinemaRepository.getFavoriteTvShowList()).thenReturn(tvShows)
        val tvShowEntities = viewModel.getFavoriteTvShows().value
        verify(cinemaRepository).getFavoriteTvShowList()
        assertNotNull(tvShowEntities)
        assertEquals(20, tvShowEntities?.size)

        viewModel.getFavoriteTvShows().observeForever(observer)
        verify(observer).onChanged(dummyTvShows)
    }
}
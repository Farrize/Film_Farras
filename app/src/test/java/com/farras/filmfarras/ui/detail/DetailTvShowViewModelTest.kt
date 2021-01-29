package com.farras.filmfarras.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.utils.DataDummy
import com.farras.filmfarras.utils.LiveDataTestUtil
import com.farras.filmfarras.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailTvShowViewModelTest {

    private lateinit var viewModel: DetailTvShowViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var cinemaRepository: CinemaRepository

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<TvShowEntity>>

    @Mock
    private lateinit var actorsObserver: Observer<Resource<List<ActorEntity>>>

    @Before
    fun setUp() {
        viewModel = DetailTvShowViewModel(cinemaRepository)
    }

    @Test
    fun getTvShow() {
        val tvShowId = DataDummy.generateDummyTvShows()[1].id
        viewModel.setSelectedTvShow(tvShowId)
        val dummyTvShow = Resource.success(DataDummy.generateDummyTvShow(tvShowId))
        val tvShow = MutableLiveData<Resource<TvShowEntity>>()
        tvShow.value = dummyTvShow

        `when`(cinemaRepository.getTvShowDetail(tvShowId)).thenReturn(tvShow)


        viewModel.tvShowResource.observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(dummyTvShow)
    }

    @Test
    fun getActorList() {
        val tvShowId = DataDummy.generateDummyTvShows()[1].id
        viewModel.setSelectedTvShow(tvShowId)
        val dummyTvShowActors = Resource.success(DataDummy.generateDummyTvShowActors(tvShowId))
        val tvShowActors = MutableLiveData<Resource<List<ActorEntity>>>()
        tvShowActors.value = dummyTvShowActors

        `when`(cinemaRepository.getTvShowActorList(tvShowId)).thenReturn(tvShowActors)
        val actorEntities = LiveDataTestUtil.getValue(viewModel.getTvShowActorList())
        print(actorEntities.toString())
        verify(cinemaRepository).getTvShowActorList(tvShowId)
        assertNotNull(actorEntities)
        assertEquals(dummyTvShowActors.data?.size, actorEntities.data?.size)

        viewModel.getTvShowActorList().observeForever(actorsObserver)
        verify(actorsObserver).onChanged(dummyTvShowActors)
    }
}
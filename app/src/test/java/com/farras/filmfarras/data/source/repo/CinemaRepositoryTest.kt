package com.farras.filmfarras.data.source.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.farras.filmfarras.data.source.local.LocalDataSource
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.remote.RemoteDataSource
import com.farras.filmfarras.util.PagedListUtil
import com.farras.filmfarras.utils.AppExecutors
import com.farras.filmfarras.utils.DataDummy
import com.farras.filmfarras.utils.LiveDataTestUtil
import com.farras.filmfarras.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class CinemaRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)

    private val cinemaRepository = FakeCinemaRepository(remote, local, appExecutors)

    @Test
    fun getMovieList() {
        val movieResponses = DataDummy.generateRemoteDummyMovies()

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getMovieList()).thenReturn(dataSourceFactory)
        cinemaRepository.getMovieList()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getMovieList()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getMovieDetail() {
        val movieId = DataDummy.generateRemoteDummyMovies()[0].id
        val movieResponse = DataDummy.generateRemoteDummyMovie(movieId)

        val dummyMovie = MutableLiveData<MovieEntity>()
        dummyMovie.value = DataDummy.generateDummyMovie(movieId)
        `when`(local.getMovieDetail(movieId)).thenReturn(dummyMovie)

        val movieEntity = LiveDataTestUtil.getValue(cinemaRepository.getMovieDetail(movieId))
        verify(local).getMovieDetail(movieId)
        assertNotNull(movieEntity)
        assertNotNull(movieEntity.data?.id)
        assertEquals(movieResponse.id, movieEntity.data?.id)
    }

    @Test
    fun getMovieActorList() {
        val movieId = DataDummy.generateRemoteDummyMovies()[0].id
        val actorResponses = DataDummy.generateRemoteDummyMovieActors(movieId)

        val dummyActors = MutableLiveData<List<ActorEntity>>()
        dummyActors.value = DataDummy.generateDummyMovieActors(movieId)
        `when`(local.getMovieActorList(movieId)).thenReturn(dummyActors)

        val actorEntities = LiveDataTestUtil.getValue(cinemaRepository.getMovieActorList(movieId))
        verify(local).getMovieActorList(movieId)
        assertNotNull(actorEntities)
        assertEquals(actorResponses.size.toLong(), actorEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteMovieList() {
        val movieResponses = DataDummy.generateRemoteDummyMovies()

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getFavoriteMovieList()).thenReturn(dataSourceFactory)
        cinemaRepository.getFavoriteMovieList()

        val movieEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyMovies()))
        verify(local).getFavoriteMovieList()
        assertNotNull(movieEntities)
        assertEquals(movieResponses.size.toLong(), movieEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShowList() {
        val tvShowResponses = DataDummy.generateRemoteDummyTvShows()

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getTvShowList()).thenReturn(dataSourceFactory)
        cinemaRepository.getTvShowList()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getTvShowList()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

    @Test
    fun getTvShowDetail() {
        val tvShowId = DataDummy.generateRemoteDummyTvShows()[1].id
        val tvShowResponse = DataDummy.generateRemoteDummyTvShow(tvShowId)

        val dummyTvShow = MutableLiveData<TvShowEntity>()
        dummyTvShow.value = DataDummy.generateDummyTvShow(tvShowId)
        `when`(local.getTvShowDetail(tvShowId)).thenReturn(dummyTvShow)

        val tvShowEntity = LiveDataTestUtil.getValue(cinemaRepository.getTvShowDetail(tvShowId))
        verify(local).getTvShowDetail(tvShowId)
        assertNotNull(tvShowEntity)
        assertNotNull(tvShowEntity.data?.id)
        assertEquals(tvShowResponse.id, tvShowEntity.data?.id)
    }

    @Test
    fun getTvShowActorList() {
        val tvShowId = DataDummy.generateRemoteDummyTvShows()[1].id
        val actorResponses = DataDummy.generateRemoteDummyTvShowActors(tvShowId)

        val dummyActors = MutableLiveData<List<ActorEntity>>()
        dummyActors.value = DataDummy.generateDummyTvShowActors(tvShowId)
        `when`(local.getTvShowActorList(tvShowId)).thenReturn(dummyActors)

        val actorEntities = LiveDataTestUtil.getValue(cinemaRepository.getTvShowActorList(tvShowId))
        verify(local).getTvShowActorList(tvShowId)
        assertNotNull(actorEntities)
        assertEquals(actorResponses.size.toLong(), actorEntities.data?.size?.toLong())
    }

    @Test
    fun getFavoriteTvShowList() {
        val tvShowResponses = DataDummy.generateRemoteDummyTvShows()

        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, TvShowEntity>
        `when`(local.getFavoriteTvShowList()).thenReturn(dataSourceFactory)
        cinemaRepository.getFavoriteTvShowList()

        val tvShowEntities = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyTvShows()))
        verify(local).getFavoriteTvShowList()
        assertNotNull(tvShowEntities)
        assertEquals(tvShowResponses.size.toLong(), tvShowEntities.data?.size?.toLong())
    }

}

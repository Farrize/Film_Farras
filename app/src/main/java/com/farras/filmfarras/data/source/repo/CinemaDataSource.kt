package com.farras.filmfarras.data.source.repo

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.farras.filmfarras.data.source.local.entity.*
import com.farras.filmfarras.vo.Resource

interface CinemaDataSource {

    fun getMovieList(): LiveData<Resource<PagedList<MovieEntity>>>

    fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>>

    fun getTvShowList(): LiveData<Resource<PagedList<TvShowEntity>>>

    fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>>

    fun getMovieActorList(id: Int): LiveData<Resource<List<ActorEntity>>>

    fun getTvShowActorList(id: Int): LiveData<Resource<List<ActorEntity>>>

    fun getFavoriteMovieList(): LiveData<PagedList<MovieEntity>>

    fun getFavoriteTvShowList(): LiveData<PagedList<TvShowEntity>>

    fun setMovieFavorite(movie: MovieEntity, state: Boolean)

    fun setTvShowFavorite(tvShow: TvShowEntity, state: Boolean)

}
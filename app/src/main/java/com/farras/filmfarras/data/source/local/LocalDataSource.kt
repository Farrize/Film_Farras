package com.farras.filmfarras.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.farras.filmfarras.data.source.local.entity.*
import com.farras.filmfarras.data.source.local.room.CinemaDao

class LocalDataSource private constructor(private val mCinemaDao: CinemaDao) {

    companion object{
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(cinemaDao: CinemaDao): LocalDataSource =
                INSTANCE ?: LocalDataSource(cinemaDao)
    }

    fun getMovieList(): DataSource.Factory<Int, MovieEntity> = mCinemaDao.getMovies()

    fun getFavoriteMovieList(): DataSource.Factory<Int, MovieEntity> = mCinemaDao.getFavoriteMovies()

    fun getMovieDetail(movieId: Int): LiveData<MovieEntity> = mCinemaDao.getMovieDetail(movieId)

    fun getMovieActorList(movieId: Int): LiveData<List<ActorEntity>> = mCinemaDao.getActorsByMovie(movieId)

    fun insertMovies(movies: List<MovieEntity>) = mCinemaDao.insertMovies(movies)

    fun setMovieFavorite(movie: MovieEntity, newState: Boolean) {
        movie.favorited = newState
        mCinemaDao.updateMovie(movie)
    }

    fun getTvShowList(): DataSource.Factory<Int, TvShowEntity> = mCinemaDao.getTvShows()

    fun getFavoriteTvShowList(): DataSource.Factory<Int, TvShowEntity> = mCinemaDao.getFavoriteTvShows()

    fun getTvShowDetail(tvshowId: Int): LiveData<TvShowEntity> = mCinemaDao.getTvShowDetail(tvshowId)

    fun getTvShowActorList(tvshowId: Int): LiveData<List<ActorEntity>> = mCinemaDao.getActorsByTvShow(tvshowId)

    fun insertTvShows(tvShows: List<TvShowEntity>) = mCinemaDao.insertTvShows(tvShows)

    fun setTvShowFavorite(tvShow: TvShowEntity, newState: Boolean) {
        tvShow.favorited = newState
        mCinemaDao.updateTvShow(tvShow)
    }

    fun insertActors(actors: List<ActorEntity>) = mCinemaDao.insertActors(actors)

}
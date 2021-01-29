package com.farras.filmfarras.data.source.local.room

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.farras.filmfarras.data.source.local.entity.*

@Dao
interface CinemaDao {

    @Query("SELECT * FROM movieentities")
    fun getMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentities WHERE favorited = 1")
    fun getFavoriteMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM movieentities WHERE movieId = :movieId")
    fun getMovieDetail(movieId: Int): LiveData<MovieEntity>

    @Query("SELECT * FROM actorentities WHERE cinemaId = :movieId")
    fun getActorsByMovie(movieId: Int): LiveData<List<ActorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovies(movies: List<MovieEntity>)

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM tvshowentities")
    fun getTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvshowentities WHERE favorited = 1")
    fun getFavoriteTvShows(): DataSource.Factory<Int, TvShowEntity>

    @Query("SELECT * FROM tvshowentities WHERE tvshowId = :tvshowId")
    fun getTvShowDetail(tvshowId: Int): LiveData<TvShowEntity>

    @Query("SELECT * FROM actorentities WHERE cinemaId = :tvshowId")
    fun getActorsByTvShow(tvshowId: Int): LiveData<List<ActorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTvShows(tvShows: List<TvShowEntity>)

    @Update
    fun updateTvShow(tvShow: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActors(actors: List<ActorEntity>)
}
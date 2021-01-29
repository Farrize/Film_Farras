package com.farras.filmfarras.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.vo.Resource

class DetailMovieViewModel(private val cinemaRepository: CinemaRepository) : ViewModel() {
    private val movieId = MutableLiveData<Int>()

    fun setSelectedMovie(movieId: Int) {
        this.movieId.value = movieId
    }

    var movieResource: LiveData<Resource<MovieEntity>> = Transformations.switchMap(movieId) { mMovieId ->
        cinemaRepository.getMovieDetail(mMovieId)
    }

    fun getMovieActorList(): LiveData<Resource<List<ActorEntity>>> = Transformations.switchMap(movieId) { mMovieId ->
        cinemaRepository.getMovieActorList(mMovieId)
    }

    fun setFavorite() {
        val movie = movieResource.value
        if (movie != null) {
            val movieEntity = movie.data

            if (movieEntity != null) {
                val newState = !movieEntity.favorited
                cinemaRepository.setMovieFavorite(movieEntity, newState)
            }
        }
    }

}
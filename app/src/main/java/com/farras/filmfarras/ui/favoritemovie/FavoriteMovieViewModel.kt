package com.farras.filmfarras.ui.favoritemovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository

class FavoriteMovieViewModel(private val cinemaRepository: CinemaRepository) : ViewModel() {

    fun getFavoriteMovies(): LiveData<PagedList<MovieEntity>> = cinemaRepository.getFavoriteMovieList()

}
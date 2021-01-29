package com.farras.filmfarras.ui.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.vo.Resource

class MovieViewModel(private val cinemaRepository: CinemaRepository) : ViewModel() {

    fun getMovies(): LiveData<Resource<PagedList<MovieEntity>>> = cinemaRepository.getMovieList()

}
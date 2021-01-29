package com.farras.filmfarras.ui.favoritetvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository

class FavoriteTvShowViewModel(private val cinemaRepository: CinemaRepository) : ViewModel() {

    fun getFavoriteTvShows(): LiveData<PagedList<TvShowEntity>> = cinemaRepository.getFavoriteTvShowList()

}
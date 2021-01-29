package com.farras.filmfarras.ui.tvshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.vo.Resource

class TvShowViewModel(private val cinemaRepository: CinemaRepository) : ViewModel() {

    fun getTvShows(): LiveData<Resource<PagedList<TvShowEntity>>> = cinemaRepository.getTvShowList()

}
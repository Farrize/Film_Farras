package com.farras.filmfarras.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.TvShowEntity
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.vo.Resource

class DetailTvShowViewModel(private val cinemaRepository: CinemaRepository) : ViewModel() {
    private var tvShowId = MutableLiveData<Int>()

    fun setSelectedTvShow(tvShowId: Int) {
        this.tvShowId.value = tvShowId
    }

    var tvShowResource: LiveData<Resource<TvShowEntity>> = Transformations.switchMap(tvShowId) { mTvShowId ->
        cinemaRepository.getTvShowDetail(mTvShowId)
    }

    fun getTvShowActorList(): LiveData<Resource<List<ActorEntity>>> = Transformations.switchMap(tvShowId) { mTvShowId ->
        cinemaRepository.getTvShowActorList(mTvShowId)
    }

    fun setFavorite() {
        val tvShow = tvShowResource.value
        if (tvShow != null) {
            val tvShowEntity = tvShow.data

            if (tvShowEntity != null) {
                val newState = !tvShowEntity.favorited
                cinemaRepository.setTvShowFavorite(tvShowEntity, newState)
            }
        }
    }

}
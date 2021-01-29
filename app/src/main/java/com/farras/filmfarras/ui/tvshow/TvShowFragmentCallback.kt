package com.farras.filmfarras.ui.tvshow

import com.farras.filmfarras.data.source.local.entity.TvShowEntity

interface TvShowFragmentCallback {
    fun onShareClick(tvShow: TvShowEntity)
}
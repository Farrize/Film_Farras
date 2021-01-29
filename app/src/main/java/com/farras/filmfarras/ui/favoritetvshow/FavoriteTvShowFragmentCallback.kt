package com.farras.filmfarras.ui.favoritetvshow

import com.farras.filmfarras.data.source.local.entity.TvShowEntity

interface FavoriteTvShowFragmentCallback {
    fun onShareClick(tvshow: TvShowEntity)
}
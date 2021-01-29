package com.farras.filmfarras.ui.favoritemovie

import com.farras.filmfarras.data.source.local.entity.MovieEntity

interface FavoriteMovieFragmentCallback {
    fun onShareClick(movie: MovieEntity)
}
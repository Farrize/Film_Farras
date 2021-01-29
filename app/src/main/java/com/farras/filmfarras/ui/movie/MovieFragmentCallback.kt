package com.farras.filmfarras.ui.movie

import com.farras.filmfarras.data.source.local.entity.MovieEntity

interface MovieFragmentCallback {
    fun onShareClick(movie: MovieEntity)
}
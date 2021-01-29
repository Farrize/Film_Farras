package com.farras.filmfarras.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.di.Injection
import com.farras.filmfarras.ui.detail.DetailMovieViewModel
import com.farras.filmfarras.ui.detail.DetailTvShowViewModel
import com.farras.filmfarras.ui.favoritemovie.FavoriteMovieViewModel
import com.farras.filmfarras.ui.favoritetvshow.FavoriteTvShowViewModel
import com.farras.filmfarras.ui.movie.MovieViewModel
import com.farras.filmfarras.ui.tvshow.TvShowViewModel

class ViewModelFactory private constructor(private val mCinemaRepository: CinemaRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
                instance ?: synchronized(this) {
                    instance ?: ViewModelFactory(Injection.provideRepository(context))
                }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(MovieViewModel::class.java) -> {
                return MovieViewModel(mCinemaRepository) as T
            }
            modelClass.isAssignableFrom(TvShowViewModel::class.java) -> {
                return TvShowViewModel(mCinemaRepository) as T
            }
            modelClass.isAssignableFrom(DetailMovieViewModel::class.java) -> {
                return DetailMovieViewModel(mCinemaRepository) as T
            }
            modelClass.isAssignableFrom(DetailTvShowViewModel::class.java) -> {
                return DetailTvShowViewModel(mCinemaRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteMovieViewModel::class.java) -> {
                return FavoriteMovieViewModel(mCinemaRepository) as T
            }
            modelClass.isAssignableFrom(FavoriteTvShowViewModel::class.java) -> {
                return FavoriteTvShowViewModel(mCinemaRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}
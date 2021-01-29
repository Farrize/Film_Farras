package com.farras.filmfarras.di

import android.content.Context
import com.farras.filmfarras.data.source.local.LocalDataSource
import com.farras.filmfarras.data.source.local.room.CinemaDatabase
import com.farras.filmfarras.data.source.remote.RemoteDataSource
import com.farras.filmfarras.data.source.repo.CinemaRepository
import com.farras.filmfarras.utils.AppExecutors

object Injection {

    fun provideRepository(context: Context): CinemaRepository {

        val database = CinemaDatabase.getInstance(context)

        val remoteDataSource = RemoteDataSource.getInstance()
        val localDataSource = LocalDataSource.getInstance(database.cinemaDao())
        val appExecutors = AppExecutors()

        return CinemaRepository.getInstance(remoteDataSource, localDataSource, appExecutors)
    }

}
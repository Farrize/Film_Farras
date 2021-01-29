package com.farras.filmfarras.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.farras.filmfarras.data.source.local.entity.ActorEntity
import com.farras.filmfarras.data.source.local.entity.MovieEntity
import com.farras.filmfarras.data.source.local.entity.TvShowEntity

@Database(entities = [MovieEntity::class, TvShowEntity::class, ActorEntity::class],
        version = 1,
        exportSchema = false)
abstract class CinemaDatabase : RoomDatabase() {
    abstract fun cinemaDao(): CinemaDao

    companion object {

        @Volatile
        private var INSTANCE: CinemaDatabase? = null

        fun getInstance(context: Context): CinemaDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(context.applicationContext,
                        CinemaDatabase::class.java,
                        "Cinema.db").build()
            }
    }
}
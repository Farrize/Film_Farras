package com.farras.filmfarras.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvshowentities")
data class TvShowEntity (
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "tvshowId")
    var id: Int,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "release")
    var release: String,

    @ColumnInfo(name = "score")
    var score: Double,

    @ColumnInfo(name = "storyline")
    var storyline: String,

    @ColumnInfo(name = "favorited")
    var favorited: Boolean = false,

    @ColumnInfo(name = "posterPath")
    var poster: String
)
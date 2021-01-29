package com.farras.filmfarras.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "actorentities")
data class ActorEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "character")
    var character: String,

    @NonNull
    @ColumnInfo(name = "cinemaId")
    var cinemaId: Int
)
package com.example.newsapp.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NewEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "iconUrl") val iconUrl : String?,
    @ColumnInfo(name = "title") val title : String?,
    @ColumnInfo(name = "subtitle") val subtitle : String?,
    @ColumnInfo(name = "web") val web : String?,
    @ColumnInfo(name = "webShare") val webShare : String?
)

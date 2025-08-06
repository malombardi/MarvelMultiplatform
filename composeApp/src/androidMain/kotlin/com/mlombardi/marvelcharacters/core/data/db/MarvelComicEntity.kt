package com.mlombardi.marvelcharacters.core.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mlombardi.marvelcharacters.core.domain.Constants

@Entity
data class MarvelComicEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "comic_id") var id: Int? = Constants.UNKNOWN_ID,
    var digitalId: Int? = Constants.UNKNOWN_ID,
    var description: String? = "",
    var thumbnail: String? = "",
    var title: String? = ""
)

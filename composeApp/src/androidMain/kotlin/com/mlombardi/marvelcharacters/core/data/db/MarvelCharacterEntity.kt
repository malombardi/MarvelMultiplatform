package com.mlombardi.marvelcharacters.core.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mlombardi.marvelcharacters.core.domain.Constants

@Entity
data class MarvelCharacterEntity (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "character_id")
    var id: Int = Constants.UNKNOWN_ID,
    var name: String? = "",
    var description: String? = "",
    var thumbnail: String? = ""
)


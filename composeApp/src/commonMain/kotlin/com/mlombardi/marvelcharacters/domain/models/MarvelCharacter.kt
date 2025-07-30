package com.mlombardi.marvelcharacters.domain.models

import com.mlombardi.marvelcharacters.domain.Constants
import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacter(
    val description: String? = "",
    val id: Int? = Constants.UNKNOWN_ID,
    val name: String? = "",
    val thumbnail: String? = null,
    val url: String? = null,
    val comicsCount: Int? = Constants.COMICS_EMPTY,
    var isFav : Boolean = false
)

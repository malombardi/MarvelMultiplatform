package com.mlombardi.marvelcharacters.data.network.responses

import com.mlombardi.marvelcharacters.domain.Constants.COMICS_EMPTY
import com.mlombardi.marvelcharacters.domain.Constants.UNKNOWN_ID

data class MarvelCharacter(
    val description: String? = "",
    val id: Int? = UNKNOWN_ID,
    val name: String? = "",
    val thumbnail: Thumbnail? = null,
    val urls: List<Url>? = null,
    val comics: Comic? = null,
)

data class Comic(
    val available : Int? =COMICS_EMPTY,
)

package com.mlombardi.marvelcharacters.characters_list.data.network.responses

import com.mlombardi.marvelcharacters.core.domain.Constants.UNKNOWN_ID

data class MarvelCharacter(
    val description: String? = "",
    val id: Int? = UNKNOWN_ID,
    val name: String? = "",
    val thumbnail: Thumbnail? = null,
    val urls: List<Url>? = null,
)

data class Thumbnail(
    val extension: String?,
    val path: String?
)

data class Url(
    val type: String?,
    val url: String?
)

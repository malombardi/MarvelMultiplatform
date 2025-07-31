package com.mlombardi.marvelcharacters.characters_list.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacter(
    val description: String? = "",
    val id: Int,
    val name: String? = "",
    val thumbnail: Thumbnail? = null,
    val urls: List<Url>? = null,
)
@Serializable
data class Thumbnail(
    val extension: String?,
    val path: String?
)
@Serializable
data class Url(
    val type: String?,
    val url: String?
)

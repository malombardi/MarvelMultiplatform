package com.mlombardi.marvelcharacters.comics_list.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class MarvelComic(
    val creators: Creators?,
    val description: String?,
    val digitalId: Int?,
    val id: Int?,
    val thumbnail: Thumbnail?,
    val title: String?
)
@Serializable
data class Thumbnail(
    val extension: String?,
    val path: String?
)

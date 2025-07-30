package com.mlombardi.marvelcharacters.comics_list.data.network.responses

data class MarvelComic(
    val creators: Creators?,
    val description: String?,
    val digitalId: Int?,
    val id: Int?,
    val thumbnail: Thumbnail?,
    val title: String?
)

data class Thumbnail(
    val extension: String?,
    val path: String?
)

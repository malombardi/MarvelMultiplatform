package com.mlombardi.marvelcharacters.comics_list.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class MarvelComicResponse(
    val data: ComicData,
)
@Serializable
data class ComicData(
    val results: List<MarvelComic>
)
@Serializable
data class Creators(
    val items: List<Creator>?
)
@Serializable
data class Creator(
    val name: String?,
    val resourceURI: String?,
    val role: String?
)

package com.mlombardi.marvelcharacters.comics_list.domain.models

import com.mlombardi.marvelcharacters.core.domain.Constants

data class MarvelComic(
    val creators: List<MarvelComicCreator>? = null,
    val description: String? = "",
    val digitalId: Int? = Constants.UNKNOWN_ID,
    val id: Int? = Constants.UNKNOWN_ID,
    val thumbnail: String? = "",
    val title: String? = ""
)

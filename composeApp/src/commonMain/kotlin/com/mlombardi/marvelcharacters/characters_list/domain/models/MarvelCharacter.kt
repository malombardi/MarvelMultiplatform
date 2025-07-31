package com.mlombardi.marvelcharacters.characters_list.domain.models

import com.mlombardi.marvelcharacters.core.domain.Constants
import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacter(
    val description: String? = "",
    val id: Int = Constants.UNKNOWN_ID,
    val name: String? = "",
    val thumbnail: String? = null,
    val url: String? = null,
)

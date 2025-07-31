package com.mlombardi.marvelcharacters.characters_list.data.network.responses

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacterResponse(
    val data: CharacterData,
)

@Serializable
data class CharacterData(
    val results: List<MarvelCharacter>
)

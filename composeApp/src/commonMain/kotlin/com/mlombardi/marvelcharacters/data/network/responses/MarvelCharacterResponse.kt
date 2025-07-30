package com.mlombardi.marvelcharacters.data.network.responses

data class MarvelCharacterResponse(
    val data: CharacterData,
)

data class CharacterData(
    val results: List<MarvelCharacter>
)

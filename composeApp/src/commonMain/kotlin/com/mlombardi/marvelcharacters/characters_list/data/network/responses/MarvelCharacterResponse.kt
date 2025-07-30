package com.mlombardi.marvelcharacters.characters_list.data.network.responses

data class MarvelCharacterResponse(
    val data: CharacterData,
)

data class CharacterData(
    val results: List<MarvelCharacter>
)

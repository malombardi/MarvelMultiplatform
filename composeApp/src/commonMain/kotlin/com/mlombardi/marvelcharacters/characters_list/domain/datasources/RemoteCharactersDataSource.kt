package com.mlombardi.marvelcharacters.characters_list.domain.datasources

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter

interface RemoteCharactersDataSource {
    suspend fun getCharacters(offset: Int): List<MarvelCharacter>

    suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter>

}

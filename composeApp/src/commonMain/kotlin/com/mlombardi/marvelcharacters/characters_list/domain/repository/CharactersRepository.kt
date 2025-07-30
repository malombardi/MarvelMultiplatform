package com.mlombardi.marvelcharacters.characters_list.domain.repository

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    fun getCharacters(offset: Int?): Flow<List<MarvelCharacter>>

    fun searchCharacters(startWith: String, offset: Int?): Flow<List<MarvelCharacter>>
}

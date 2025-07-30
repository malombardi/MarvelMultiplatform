package com.mlombardi.marvelcharacters.characters_list.domain.datasources

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import kotlinx.coroutines.flow.Flow

interface LocalCharactersDataSource {
    suspend fun charactersSize(): Int

    suspend fun searchSize(startWith: String): Int

    suspend fun saveCharacters(characters: List<MarvelCharacter>)

    fun getCharacters(): Flow<List<MarvelCharacter>>

    fun searchCharacters(startWith: String): Flow<List<MarvelCharacter>>

}

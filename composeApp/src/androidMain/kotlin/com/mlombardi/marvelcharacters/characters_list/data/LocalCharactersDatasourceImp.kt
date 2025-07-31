package com.mlombardi.marvelcharacters.characters_list.data

import com.mlombardi.marvelcharacters.characters_list.domain.datasources.LocalCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class LocalCharactersDatasourceImp: LocalCharactersDataSource {
    override suspend fun charactersSize(): Int  = 0

    override suspend fun searchSize(startWith: String): Int = 0

    override suspend fun saveCharacters(characters: List<MarvelCharacter>) {}

    override fun getCharacters(): Flow<List<MarvelCharacter>> {
        return emptyFlow<List<MarvelCharacter>>()
    }

    override fun searchCharacters(startWith: String): Flow<List<MarvelCharacter>> {
        return emptyFlow<List<MarvelCharacter>>()
    }
}

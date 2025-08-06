package com.mlombardi.marvelcharacters.characters_list.data

import com.mlombardi.marvelcharacters.characters_list.domain.datasources.LocalCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.core.data.db.MarvelDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalCharactersDatasourceImpl(private val dao: MarvelDao) : LocalCharactersDataSource {
    override suspend fun charactersSize(): Int {
        return dao.charactersCount()
    }

    override suspend fun searchSize(startWith: String): Int {
        return dao.searchCount(startWith)
    }

    override suspend fun saveCharacters(characters: List<MarvelCharacter>) {
        dao.saveCharacter(characters.toLocalCharacterList())
    }

    override fun getCharacters(): Flow<List<MarvelCharacter>> =
        dao.getCharacters().map { localCharacter ->
            localCharacter.map { it.toDomainCharacter() }
        }

    override fun searchCharacters(startWith: String): Flow<List<MarvelCharacter>> =
        dao.getCharactersWithName(startWith).map { localCharacter ->
            localCharacter.map { it.toDomainCharacter() }
        }
}

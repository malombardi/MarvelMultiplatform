package com.mlombardi.marvelcharacters.characters_list.data

import com.mlombardi.marvelcharacters.characters_list.domain.datasources.RemoteCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.domain.repository.CharactersRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class CharactersRepositoryImpl(private val remoteCharactersDataSource: RemoteCharactersDataSource) :
    CharactersRepository {
    override fun getCharacters(offset: Int?): Flow<List<MarvelCharacter>> {
        var result: List<MarvelCharacter> = listOf()
        val lastVisible = offset ?: 0
        MainScope().launch {
            result = remoteCharactersDataSource.getCharacters(lastVisible)
        }
        return flow { emit(result) }
    }

    override fun searchCharacters(
        startWith: String,
        offset: Int?
    ): Flow<List<MarvelCharacter>> {
        var result: List<MarvelCharacter> = listOf()
        val lastVisible = offset ?: 0
        MainScope().launch {
            result = remoteCharactersDataSource.searchCharacters(startWith, lastVisible)
        }
        return flow { emit(result) }
    }
}

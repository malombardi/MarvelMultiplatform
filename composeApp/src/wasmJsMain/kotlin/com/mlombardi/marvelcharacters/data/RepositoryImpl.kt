package com.mlombardi.marvelcharacters.data

import com.mlombardi.marvelcharacters.domain.datasources.RemoteDataSource
import com.mlombardi.marvelcharacters.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.domain.repository.Repository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class RepositoryImpl(private val datasource: RemoteDataSource) : Repository {
    override fun getCharacters(offset: Int?): Flow<List<MarvelCharacter>> {
        var result: List<MarvelCharacter> = listOf()
        val lastVisible = offset ?: 0
        MainScope().launch {
            result = datasource.getCharacters(lastVisible)
        }
        return flow { emit(result) }
    }

    override fun searchCharacters(startWith: String, offset: Int?): Flow<List<MarvelCharacter>> {
        var result: List<MarvelCharacter> = listOf()
        val lastVisible = offset ?: 0
        MainScope().launch {
            result = datasource.searchCharacters(startWith, lastVisible)
        }
        return flow { emit(result) }
    }

    override fun getCharacterComics(characterId: String, offset: Int?): Flow<List<MarvelComic>> {
        var result: List<MarvelComic> = listOf()
        val lastVisible = offset ?: 0
        MainScope().launch {
            result = datasource.getComics(characterId, lastVisible)
        }
        return flow { emit(result) }
    }

}
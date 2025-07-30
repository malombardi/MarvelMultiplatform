package com.mlombardi.marvelcharacters.domain.datasources

import com.mlombardi.marvelcharacters.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.domain.models.MarvelComic

interface RemoteDataSource {
    suspend fun getCharacters(offset: Int): List<MarvelCharacter>

    suspend fun searchCharacters(startWith: String, offset: Int): List<MarvelCharacter>

    suspend fun getComics(characterId: String, offset: Int): List<MarvelComic>
}

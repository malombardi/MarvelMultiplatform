package com.mlombardi.marvelcharacters.domain.repository

import androidx.compose.ui.geometry.Offset
import com.mlombardi.marvelcharacters.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.domain.models.MarvelComic
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getCharacters(offset: Int?): Flow<List<MarvelCharacter>>

    fun searchCharacters(startWith: String, offset: Int?): Flow<List<MarvelCharacter>>

    fun getCharacterComics(characterId: String, offset: Int?): Flow<List<MarvelComic>>

}

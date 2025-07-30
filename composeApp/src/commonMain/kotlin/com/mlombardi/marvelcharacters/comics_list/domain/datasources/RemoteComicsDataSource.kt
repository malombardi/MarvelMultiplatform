package com.mlombardi.marvelcharacters.comics_list.domain.datasources

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic

interface RemoteComicsDataSource {
    suspend fun getComicsForCharacter(characterId: String, offset: Int): List<MarvelComic>
    suspend fun getComics(offset: Int): List<MarvelComic>
}

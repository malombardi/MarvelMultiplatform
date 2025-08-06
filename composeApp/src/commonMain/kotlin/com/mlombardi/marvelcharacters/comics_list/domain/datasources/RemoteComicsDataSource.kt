package com.mlombardi.marvelcharacters.comics_list.domain.datasources

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic

interface RemoteComicsDataSource {
    suspend fun getComics(offset: Int): List<MarvelComic>
}

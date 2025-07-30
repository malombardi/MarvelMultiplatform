package com.mlombardi.marvelcharacters.comics_list.domain.datasources

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import kotlinx.coroutines.flow.Flow

interface LocalComicsDataSource {
    suspend fun comicsSize(): Int

    suspend fun saveComics(comics: List<MarvelComic>)

    fun getComics(): Flow<List<MarvelComic>>
}

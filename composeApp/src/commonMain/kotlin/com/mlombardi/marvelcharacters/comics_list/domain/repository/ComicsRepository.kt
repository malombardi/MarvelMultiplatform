package com.mlombardi.marvelcharacters.comics_list.domain.repository

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import kotlinx.coroutines.flow.Flow

interface ComicsRepository {
    fun getComics(offset: Int?): Flow<List<MarvelComic>>
}

package com.mlombardi.marvelcharacters.comics_list.data

import com.mlombardi.marvelcharacters.comics_list.domain.datasources.RemoteComicsDataSource
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.comics_list.domain.repository.ComicsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ComicsRepositoryImpl(private val remoteComicsDataSource: RemoteComicsDataSource) :
    ComicsRepository {
    override fun getComics(offset: Int?): Flow<List<MarvelComic>> = flow {
        val lastVisible = offset ?: 0
        val characters = remoteComicsDataSource.getComics(lastVisible)  // suspend
        emit(characters)
    }
}

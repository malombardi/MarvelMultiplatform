package com.mlombardi.marvelcharacters.comics_list.data

import com.mlombardi.marvelcharacters.comics_list.domain.datasources.RemoteComicsDataSource
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.comics_list.domain.repository.ComicsRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ComicsRepositoryImpl(private val remoteComicsDataSource: RemoteComicsDataSource) :
    ComicsRepository {
    override fun getComics(offset: Int?): Flow<List<MarvelComic>> {
        var result: List<MarvelComic> = listOf()
        val lastVisible = offset ?: 0
        MainScope().launch {
            result = remoteComicsDataSource.getComics(lastVisible)
        }
        return flow { emit(result) }
    }
}

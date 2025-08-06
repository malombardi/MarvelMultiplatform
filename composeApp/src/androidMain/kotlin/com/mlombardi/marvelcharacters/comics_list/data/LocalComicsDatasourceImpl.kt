package com.mlombardi.marvelcharacters.comics_list.data

import com.mlombardi.marvelcharacters.comics_list.domain.datasources.LocalComicsDataSource
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.core.data.db.MarvelDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalComicsDatasourceImpl(private val dao: MarvelDao) : LocalComicsDataSource {
    override suspend fun comicsSize(): Int {
        return dao.comicsCount()
    }

    override suspend fun saveComics(comics: List<MarvelComic>) {
        dao.saveComic(comics.toLocalComicsList())
    }


    override fun getComics(): Flow<List<MarvelComic>> =
        dao.getComics().map { localComic ->
            localComic.map { it.toDomainComic() }
        }

}

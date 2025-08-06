package com.mlombardi.marvelcharacters.comics_list.data

import com.mlombardi.marvelcharacters.comics_list.domain.datasources.LocalComicsDataSource
import com.mlombardi.marvelcharacters.comics_list.domain.datasources.RemoteComicsDataSource
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.comics_list.domain.repository.ComicsRepository
import com.mlombardi.marvelcharacters.core.domain.Constants.PAGE_SIZE
import com.mlombardi.marvelcharacters.core.domain.Constants.PAGE_THRESHOLD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class ComicsRepositoryImpl(
    private val localComicsDataSource: LocalComicsDataSource,
    private val remoteComicsDataSource: RemoteComicsDataSource
) : ComicsRepository {

    override fun getComics(offset: Int?): Flow<List<MarvelComic>> {
        val result = localComicsDataSource.getComics()
        offset?.let {
            CoroutineScope(Dispatchers.IO).launch {
                checkComicsRequireNewPage(it)
            }
        }
        return result
    }

    suspend fun checkComicsRequireNewPage(
        lastVisible: Int
    ) {
        val size = localComicsDataSource.comicsSize()
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = if (lastVisible == 0) 0 else {
                (size / PAGE_SIZE) * PAGE_SIZE
            }
            val newCharacters = withTimeout(10_000) { remoteComicsDataSource.getComics(offset) }
            localComicsDataSource.saveComics(newCharacters)
        }
    }
}

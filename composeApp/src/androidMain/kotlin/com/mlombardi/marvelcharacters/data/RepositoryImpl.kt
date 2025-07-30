package com.mlombardi.marvelcharacters.data

import com.mlombardi.marvelcharacters.domain.Constants.PAGE_SIZE
import com.mlombardi.marvelcharacters.domain.Constants.PAGE_THRESHOLD
import com.mlombardi.marvelcharacters.domain.datasources.LocalDataSource
import com.mlombardi.marvelcharacters.domain.datasources.RemoteDataSource
import com.mlombardi.marvelcharacters.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.domain.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class RepositoryImpl(private val localDataSource: LocalDataSource,
                     private val remoteDataSource: RemoteDataSource) : Repository{

    override fun getCharacters(offset: Int?): Flow<List<MarvelCharacter>> {
        val result = localDataSource.getCharacters()
        offset?.let {
            CoroutineScope(Dispatchers.IO).launch {
                checkCharactersRequireNewPage(it)
            }
        }
        return result
    }

    suspend fun checkCharactersRequireNewPage(lastVisible: Int) {
        val size = localDataSource.charactersSize()
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = if(lastVisible == 0) 0 else {(size / PAGE_SIZE) * PAGE_SIZE}
            val newCharacters = withTimeout(5_000) { remoteDataSource.getCharacters(offset) }
            localDataSource.saveCharacters(newCharacters)
        }
    }

    override fun searchCharacters(startWith: String, offset: Int?): Flow<List<MarvelCharacter>> =
        localDataSource.searchCharacters(startWith)

    suspend fun checkSearchRequireNewPage(startWith: String, lastVisible: Int) {
        val size = localDataSource.searchSize(startWith)
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = if(lastVisible == 0) 0 else {(size / PAGE_SIZE) * PAGE_SIZE}
            val newCharacters =
                withTimeout(5_000) { remoteDataSource.searchCharacters(startWith, offset) }
            localDataSource.saveSearch(newCharacters)
        }
    }

    override fun getCharacterComics(characterId: String, offset: Int?): Flow<List<MarvelComic>> =
        localDataSource.getComics(characterId)

    suspend fun checkComicsRequireNewPage(characterId: String, lastVisible: Int) {
        val size = localDataSource.comicsSize(characterId)
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = if(lastVisible == 0) 0 else {(size / PAGE_SIZE) * PAGE_SIZE}
            val newComics = withTimeout(5_000) { remoteDataSource.getComics(characterId, offset) }
            localDataSource.saveComics(characterId, newComics)
        }
    }
}

package com.mlombardi.marvelcharacters.characters_list.data

import com.mlombardi.marvelcharacters.characters_list.domain.datasources.LocalCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.datasources.RemoteCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.domain.repository.CharactersRepository
import com.mlombardi.marvelcharacters.core.domain.Constants.PAGE_SIZE
import com.mlombardi.marvelcharacters.core.domain.Constants.PAGE_THRESHOLD
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

class CharactersRepositoryImpl(private val localCharactersDataSource: LocalCharactersDataSource,
    private val remoteCharactersDataSource: RemoteCharactersDataSource): CharactersRepository {
    override fun getCharacters(offset: Int?): Flow<List<MarvelCharacter>> {
        val result = localCharactersDataSource.getCharacters()
        offset?.let {
            CoroutineScope(Dispatchers.IO).launch {
                checkCharactersRequireNewPage(it)
            }
        }
        return result
    }

    override fun searchCharacters(
        startWith: String,
        offset: Int?
    ): Flow<List<MarvelCharacter>> = localCharactersDataSource.searchCharacters(startWith)

    suspend fun checkCharactersRequireNewPage(lastVisible: Int) {
        val size = localCharactersDataSource.charactersSize()
        if (lastVisible >= size - PAGE_THRESHOLD) {
            val offset = if(lastVisible == 0) 0 else {(size / PAGE_SIZE) * PAGE_SIZE}
            val newCharacters = withTimeout(5_000) { remoteCharactersDataSource.getCharacters(offset) }
            localCharactersDataSource.saveCharacters(newCharacters)
        }
    }
}
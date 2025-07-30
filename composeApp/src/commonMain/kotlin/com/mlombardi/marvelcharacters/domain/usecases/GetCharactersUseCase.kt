package com.mlombardi.marvelcharacters.domain.usecases

import com.mlombardi.marvelcharacters.domain.errors.IErrorHandler
import com.mlombardi.marvelcharacters.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.domain.repository.Repository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase (
    private val repository: Repository,
    coroutineDispatcher: CoroutineDispatcher,
    errorHandler: IErrorHandler
) : FlowUseCase<Int?, List<MarvelCharacter>>(coroutineDispatcher, errorHandler) {

    override suspend fun execute(parameters: Int?): Flow<List<MarvelCharacter>> {
        return repository.getCharacters(parameters)
    }
}

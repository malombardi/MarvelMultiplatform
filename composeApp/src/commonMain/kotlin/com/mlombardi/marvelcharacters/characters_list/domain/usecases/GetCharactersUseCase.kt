package com.mlombardi.marvelcharacters.characters_list.domain.usecases

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.domain.repository.CharactersRepository
import com.mlombardi.marvelcharacters.core.domain.errors.IErrorHandler
import com.mlombardi.marvelcharacters.core.domain.usecases.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetCharactersUseCase (
    private val repository: CharactersRepository,
    coroutineDispatcher: CoroutineDispatcher,
    errorHandler: IErrorHandler
) : FlowUseCase<Int?, List<MarvelCharacter>>(coroutineDispatcher, errorHandler) {

    override suspend fun execute(parameters: Int?): Flow<List<MarvelCharacter>> {
        return repository.getCharacters(parameters)
    }
}

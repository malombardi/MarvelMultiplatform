package com.mlombardi.marvelcharacters.comics_list.domain.usecases

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.comics_list.domain.repository.ComicsRepository
import com.mlombardi.marvelcharacters.core.domain.errors.IErrorHandler
import com.mlombardi.marvelcharacters.core.domain.usecases.FlowUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetComicsUseCase(
    private val repository: ComicsRepository,
    coroutineDispatcher: CoroutineDispatcher,
    errorHandler: IErrorHandler
) : FlowUseCase<Int?, List<MarvelComic>>(coroutineDispatcher, errorHandler) {

    override suspend fun execute(parameters: Int?): Flow<List<MarvelComic>> {
        return repository.getComics(parameters)
    }
}

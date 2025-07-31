package com.mlombardi.marvelcharacters.di

import com.mlombardi.marvelcharacters.IODispatcher
import com.mlombardi.marvelcharacters.characters_list.data.network.RemoteCharactersDataSourceImpl
import com.mlombardi.marvelcharacters.characters_list.domain.datasources.RemoteCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.usecases.GetCharactersUseCase
import com.mlombardi.marvelcharacters.characters_list.presentation.CharacterListViewModel
import com.mlombardi.marvelcharacters.comics_list.data.network.RemoteComicsDataSourceImpl
import com.mlombardi.marvelcharacters.comics_list.domain.datasources.RemoteComicsDataSource
import com.mlombardi.marvelcharacters.core.data.errors.ErrorHandler
import com.mlombardi.marvelcharacters.core.data.network.HttpClientFactory
import com.mlombardi.marvelcharacters.core.domain.errors.IErrorHandler
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::RemoteCharactersDataSourceImpl).bind<RemoteCharactersDataSource>()
    singleOf(::RemoteComicsDataSourceImpl).bind<RemoteComicsDataSource>()
    single<CoroutineDispatcher> { IODispatcher }
    single<IErrorHandler> { ErrorHandler() }
    factory { GetCharactersUseCase(get(), get(), get()) }
    single { CharacterListViewModel(get()) }
}
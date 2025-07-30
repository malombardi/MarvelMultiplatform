package com.mlombardi.marvelcharacters.di

import com.mlombardi.marvelcharacters.characters_list.data.network.RemoteCharactersDataSourceImpl
import com.mlombardi.marvelcharacters.characters_list.domain.datasources.RemoteCharactersDataSource
import com.mlombardi.marvelcharacters.comics_list.data.network.RemoteComicsDataSourceImpl
import com.mlombardi.marvelcharacters.comics_list.domain.datasources.RemoteComicsDataSource
import com.mlombardi.marvelcharacters.core.data.network.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::RemoteCharactersDataSourceImpl).bind<RemoteCharactersDataSource>()
    singleOf(::RemoteComicsDataSourceImpl).bind<RemoteComicsDataSource>()

}
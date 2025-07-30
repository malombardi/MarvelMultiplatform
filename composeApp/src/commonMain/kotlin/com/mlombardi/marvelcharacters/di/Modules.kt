package com.mlombardi.marvelcharacters.di

import com.mlombardi.marvelcharacters.data.network.HttpClientFactory
import com.mlombardi.marvelcharacters.data.network.RemoteDataSourceImpl
import com.mlombardi.marvelcharacters.domain.datasources.RemoteDataSource
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::RemoteDataSourceImpl).bind<RemoteDataSource>()

}
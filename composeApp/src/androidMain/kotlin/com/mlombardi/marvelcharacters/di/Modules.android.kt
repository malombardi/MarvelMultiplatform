package com.mlombardi.marvelcharacters.di

import com.mlombardi.marvelcharacters.data.RepositoryImpl
import com.mlombardi.marvelcharacters.domain.repository.Repository
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        singleOf(::RepositoryImpl).bind<Repository>()
    }

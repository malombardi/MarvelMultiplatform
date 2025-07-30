package com.mlombardi.marvelcharacters.di

import com.mlombardi.marvelcharacters.characters_list.data.CharactersRepositoryImpl
import com.mlombardi.marvelcharacters.characters_list.domain.repository.CharactersRepository
import com.mlombardi.marvelcharacters.comics_list.data.ComicsRepositoryImpl
import com.mlombardi.marvelcharacters.comics_list.domain.repository.ComicsRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        singleOf(::CharactersRepositoryImpl).bind<CharactersRepository>()
        singleOf(::ComicsRepositoryImpl).bind<ComicsRepository>()
    }

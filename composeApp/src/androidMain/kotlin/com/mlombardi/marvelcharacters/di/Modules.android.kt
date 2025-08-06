package com.mlombardi.marvelcharacters.di

import androidx.room.Room
import com.mlombardi.marvelcharacters.characters_list.data.CharactersRepositoryImpl
import com.mlombardi.marvelcharacters.characters_list.data.LocalCharactersDatasourceImpl
import com.mlombardi.marvelcharacters.characters_list.domain.datasources.LocalCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.repository.CharactersRepository
import com.mlombardi.marvelcharacters.comics_list.data.ComicsRepositoryImpl
import com.mlombardi.marvelcharacters.comics_list.data.LocalComicsDatasourceImpl
import com.mlombardi.marvelcharacters.comics_list.domain.datasources.LocalComicsDataSource
import com.mlombardi.marvelcharacters.comics_list.domain.repository.ComicsRepository
import com.mlombardi.marvelcharacters.core.data.db.MarvelDao
import com.mlombardi.marvelcharacters.core.data.db.MarvelDatabase
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
        single {
            Room.databaseBuilder(
                androidApplication(),
                MarvelDatabase::class.java,
                MarvelDatabase.DB_NAME
            ).build()
        }
        single<MarvelDao> {
            val database = get<MarvelDatabase>()
            database.getMarvelDao()
        }
        singleOf(::CharactersRepositoryImpl).bind<CharactersRepository>()
        singleOf(::ComicsRepositoryImpl).bind<ComicsRepository>()
        single { LocalCharactersDatasourceImpl(get()) }.bind<LocalCharactersDataSource>()
        single { LocalComicsDatasourceImpl(get()) }.bind<LocalComicsDataSource>()
    }

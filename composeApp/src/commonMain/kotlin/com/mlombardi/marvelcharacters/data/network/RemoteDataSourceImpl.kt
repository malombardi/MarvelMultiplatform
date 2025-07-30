package com.mlombardi.marvelcharacters.data.network

import com.mlombardi.marvelcharacters.data.network.responses.MarvelCharacterResponse
import com.mlombardi.marvelcharacters.data.network.responses.MarvelComicResponse
import com.mlombardi.marvelcharacters.data.toDomainCharacterList
import com.mlombardi.marvelcharacters.data.toDomainComicList
import com.mlombardi.marvelcharacters.domain.Constants.BASE_URL
import com.mlombardi.marvelcharacters.domain.datasources.RemoteDataSource
import com.mlombardi.marvelcharacters.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.domain.models.MarvelComic
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RemoteDataSourceImpl(val client: HttpClient) : RemoteDataSource {

    override suspend fun getCharacters(offset: Int): List<MarvelCharacter> {
        return safeCall<MarvelCharacterResponse> {
            client.get(
                urlString = "$BASE_URL/characters"
            ) {
                NetworkFactory.getNetworkOptions(offset, true).forEach { (key, value) ->
                    parameter(key, value)
                }
            }
        }.data!!.toDomainCharacterList()
    }

    override suspend fun searchCharacters(
        startWith: String,
        offset: Int
    ): List<MarvelCharacter> {
        return safeCall<MarvelCharacterResponse> {
            client.get(
                urlString = "$BASE_URL/characters"
            ) {
                NetworkFactory.getNetworkOptions(offset, true).forEach { (key, value) ->
                    parameter(key, value)
                }
                parameter("nameStartsWith", startWith)
            }
        }.data!!.toDomainCharacterList()
    }

    override suspend fun getComics(
        characterId: String,
        offset: Int
    ): List<MarvelComic> {
        return safeCall<MarvelComicResponse> {
            client.get(
                urlString = "$BASE_URL/characters/$characterId/comics"
            ) {
                NetworkFactory.getNetworkOptions(offset, true).forEach { (key, value) ->
                    parameter(key, value)
                }
            }
        }.data!!.toDomainComicList()
    }
}

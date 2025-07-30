package com.mlombardi.marvelcharacters.characters_list.data.network

import com.mlombardi.marvelcharacters.characters_list.domain.datasources.RemoteCharactersDataSource
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.core.data.network.NetworkFactory
import com.mlombardi.marvelcharacters.characters_list.data.network.responses.MarvelCharacterResponse
import com.mlombardi.marvelcharacters.core.data.network.safeCall
import com.mlombardi.marvelcharacters.characters_list.data.toDomainCharacterList
import com.mlombardi.marvelcharacters.core.domain.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RemoteCharactersDataSourceImpl(val client: HttpClient) : RemoteCharactersDataSource {

    override suspend fun getCharacters(offset: Int): List<MarvelCharacter> {
        return safeCall<MarvelCharacterResponse> {
            client.get(
                urlString = "$BASE_URL/characters"
            ) {
                NetworkFactory.Companion.getNetworkOptions(offset, true).forEach { (key, value) ->
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
                NetworkFactory.Companion.getNetworkOptions(offset, true).forEach { (key, value) ->
                    parameter(key, value)
                }
                parameter("nameStartsWith", startWith)
            }
        }.data!!.toDomainCharacterList()
    }

}

package com.mlombardi.marvelcharacters.comics_list.data.network

import com.mlombardi.marvelcharacters.comics_list.data.network.responses.MarvelComicResponse
import com.mlombardi.marvelcharacters.comics_list.data.toDomainComicList
import com.mlombardi.marvelcharacters.comics_list.domain.datasources.RemoteComicsDataSource
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.core.data.network.NetworkFactory
import com.mlombardi.marvelcharacters.core.data.network.safeCall
import com.mlombardi.marvelcharacters.core.domain.Constants.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class RemoteComicsDataSourceImpl(val client: HttpClient) : RemoteComicsDataSource {

    override suspend fun getComics(offset: Int): List<MarvelComic> {
        return safeCall<MarvelComicResponse> {
            client.get(
                urlString = "$BASE_URL/comics"
            ) {
                NetworkFactory.Companion.getNetworkOptions(offset, false).forEach { (key, value) ->
                    parameter(key, value)
                }
            }
        }.data!!.toDomainComicList()
    }

}

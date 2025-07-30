package com.mlombardi.marvelcharacters.data

import com.mlombardi.marvelcharacters.domain.Constants
import com.mlombardi.marvelcharacters.domain.Constants.IMAGE_DEFAULT_SIZE
import com.mlombardi.marvelcharacters.domain.Constants.URL_BIO_TYPE
import com.mlombardi.marvelcharacters.domain.models.MarvelCommicCreator
import com.mlombardi.marvelcharacters.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.data.network.responses.MarvelCharacterResponse as RemoteCharacter
import com.mlombardi.marvelcharacters.data.network.responses.MarvelComicResponse as RemoteComic

fun RemoteCharacter.toDomainCharacterList(): List<MarvelCharacter> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + "." + it.thumbnail.extension
        } else {
            ""
        }
        val url = if (!it.urls.isNullOrEmpty()) {
            var tempUrl = ""
            for (url in it.urls) {
                if (url.type == URL_BIO_TYPE) {
                    tempUrl = url.url!!
                    break
                }
            }
            tempUrl
        } else {
            ""
        }
        MarvelCharacter(
            description = it.description,
            id = it.id,
            name = it.name,
            thumbnail = img,
            url = url,
            comicsCount = it.comics?.available ?: Constants.COMICS_EMPTY
        )
    }
}

fun RemoteComic.toDomainComicList(): List<MarvelComic> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + "." + it.thumbnail.extension
        } else {
            ""
        }
        val creators = if (it.creators != null && !it.creators.items.isNullOrEmpty()) {
            val creatorsList = ArrayList<MarvelCommicCreator>()
            for (creatorItem in it.creators.items) {
                creatorsList.add(
                    MarvelCommicCreator(
                        resourceURI = creatorItem.resourceURI,
                        name = creatorItem.name,
                        role = creatorItem.role
                    )
                )
            }
            creatorsList
        } else {
            listOf<MarvelCommicCreator>()
        }
        MarvelComic(
            creators = creators,
            description = it.description,
            digitalId = it.digitalId,
            id = it.id,
            thumbnail = img,
            title = it.title
        )
    }
}

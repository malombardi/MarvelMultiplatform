package com.mlombardi.marvelcharacters.characters_list.data

import com.mlombardi.marvelcharacters.characters_list.data.network.responses.MarvelCharacterResponse
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.core.domain.Constants.IMAGE_DEFAULT_SIZE
import com.mlombardi.marvelcharacters.core.domain.Constants.URL_BIO_TYPE

fun MarvelCharacterResponse.toDomainCharacterList(): List<MarvelCharacter> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            (it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + "." + it.thumbnail.extension)
                .replace("http://", "https://")
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
            url = url
        )
    }
}

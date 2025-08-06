package com.mlombardi.marvelcharacters.comics_list.data

import com.mlombardi.marvelcharacters.comics_list.data.network.responses.MarvelComicResponse
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.core.domain.Constants.IMAGE_DEFAULT_SIZE

fun MarvelComicResponse.toDomainComicList(): List<MarvelComic> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            (it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + "." + it.thumbnail.extension)
                .replace("http://", "https://")
        } else {
            ""
        }
        MarvelComic(
            description = it.description,
            id = it.id,
            thumbnail = img,
            title = it.title
        )
    }
}

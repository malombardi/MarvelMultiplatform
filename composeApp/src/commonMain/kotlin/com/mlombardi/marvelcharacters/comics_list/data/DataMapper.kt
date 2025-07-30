package com.mlombardi.marvelcharacters.comics_list.data

import com.mlombardi.marvelcharacters.comics_list.data.network.responses.MarvelComicResponse
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComicCreator
import com.mlombardi.marvelcharacters.core.domain.Constants.IMAGE_DEFAULT_SIZE

fun MarvelComicResponse.toDomainComicList(): List<MarvelComic> {
    return data.results.map {
        val img = if (it.thumbnail != null) {
            it.thumbnail.path + "/" + IMAGE_DEFAULT_SIZE + "." + it.thumbnail.extension
        } else {
            ""
        }
        val creators = if (it.creators != null && !it.creators.items.isNullOrEmpty()) {
            val creatorsList = ArrayList<MarvelComicCreator>()
            for (creatorItem in it.creators.items) {
                creatorsList.add(
                    MarvelComicCreator(
                        resourceURI = creatorItem.resourceURI,
                        name = creatorItem.name,
                        role = creatorItem.role
                    )
                )
            }
            creatorsList
        } else {
            listOf<MarvelComicCreator>()
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

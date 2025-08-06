package com.mlombardi.marvelcharacters.comics_list.data


import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.core.data.db.MarvelComicEntity


fun List<MarvelComic>.toLocalComicsList(): List<MarvelComicEntity> {
    return this.map {
        MarvelComicEntity(
            id = it.id,
            title = it.title,
            description = it.description,
            thumbnail = it.thumbnail
        )
    }
}

fun MarvelComicEntity.toDomainComic(): MarvelComic {
    return MarvelComic(
        description = this.description,
        id = this.id,
        title = this.title,
        thumbnail = this.thumbnail
    )
}

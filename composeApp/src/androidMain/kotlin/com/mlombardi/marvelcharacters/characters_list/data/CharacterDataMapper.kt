package com.mlombardi.marvelcharacters.characters_list.data

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.core.data.db.MarvelCharacterEntity


fun List<MarvelCharacter>.toLocalCharacterList(): List<MarvelCharacterEntity> {
    return this.map {
        MarvelCharacterEntity(
            id = it.id,
            name = it.name,
            description = it.description,
            thumbnail = it.thumbnail
        )
    }
}

fun MarvelCharacterEntity.toDomainCharacter(): MarvelCharacter {
    return MarvelCharacter(
        description = this.description,
        id = this.id,
        name = this.name,
        thumbnail = this.thumbnail,
    )
}

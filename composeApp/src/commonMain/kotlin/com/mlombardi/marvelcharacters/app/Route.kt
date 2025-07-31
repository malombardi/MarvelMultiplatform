package com.mlombardi.marvelcharacters.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object CharactersGraph : Route

    @Serializable
    data object CharactersList : Route

    @Serializable
    data class CharactersDetail(val id: String) : Route
}
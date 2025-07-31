package com.mlombardi.marvelcharacters.characters_list.presentation

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.core.presentation.UiText

data class CharactersListState (
    val results : List<MarvelCharacter> = emptyList(),
    val isLoading: Boolean = false,
    val lastVisible: Int = 0,
    val errorMessage: UiText? = null
)

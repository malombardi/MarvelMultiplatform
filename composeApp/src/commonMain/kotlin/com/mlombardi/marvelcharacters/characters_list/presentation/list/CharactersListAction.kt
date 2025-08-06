package com.mlombardi.marvelcharacters.characters_list.presentation.list

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter

sealed interface CharactersListAction {
    data class OnCharacterClicked(val character: MarvelCharacter) : CharactersListAction
    data class OnListScrolled(val lastItemVisibleIndex: Int) : CharactersListAction
    object OnGoToComicsClicked : CharactersListAction
}

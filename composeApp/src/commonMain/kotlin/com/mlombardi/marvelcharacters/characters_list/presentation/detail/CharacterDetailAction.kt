package com.mlombardi.marvelcharacters.characters_list.presentation.detail

sealed interface CharacterDetailAction {
    object OnCloseClicked : CharacterDetailAction
}

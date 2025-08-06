package com.mlombardi.marvelcharacters.comics_list.presentation.detail

sealed interface ComicDetailAction {
    object OnCloseClicked : ComicDetailAction
}

package com.mlombardi.marvelcharacters.comics_list.presentation.list

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic

sealed interface ComicsListAction {
    data class OnComicClicked (val comic: MarvelComic) : ComicsListAction
    data class OnListScrolled (val lastItemVisibleIndex: Int) : ComicsListAction
}

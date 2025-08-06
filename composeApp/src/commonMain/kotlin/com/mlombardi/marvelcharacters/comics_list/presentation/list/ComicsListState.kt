package com.mlombardi.marvelcharacters.comics_list.presentation.list

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.core.presentation.UiText

data class ComicsListState (
    val results : List<MarvelComic> = emptyList(),
    val isLoading: Boolean = false,
    val lastVisible: Int = 0,
    val errorMessage: UiText? = null
)

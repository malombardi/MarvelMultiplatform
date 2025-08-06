package com.mlombardi.marvelcharacters.comics_list.presentation.detail

import androidx.lifecycle.ViewModel
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ComicDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(ComicDetailState())
    val state = _state.asStateFlow()

    private lateinit var selectedComic: MarvelComic

    fun selectComic(comic: MarvelComic) {
        selectedComic = comic
        _state.update {
            it.copy(
                comic = selectedComic
            )
        }
    }
}

package com.mlombardi.marvelcharacters.comics_list.presentation

import androidx.lifecycle.ViewModel
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedComicViewModel : ViewModel() {
    private val _selectedComic = MutableStateFlow<MarvelComic?>(null)
    val selectedComic = _selectedComic.asStateFlow()

    fun onSelectComic(comic: MarvelComic?) {
        _selectedComic.value = comic
    }
}

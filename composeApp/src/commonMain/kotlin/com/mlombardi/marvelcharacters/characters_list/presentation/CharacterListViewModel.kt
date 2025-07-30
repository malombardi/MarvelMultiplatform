package com.mlombardi.marvelcharacters.characters_list.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CharacterListViewModel: ViewModel() {
    private val _state = MutableStateFlow(CharactersListState())
    val state = _state.asStateFlow()
}

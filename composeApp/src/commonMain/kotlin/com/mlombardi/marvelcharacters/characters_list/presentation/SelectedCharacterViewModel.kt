package com.mlombardi.marvelcharacters.characters_list.presentation

import androidx.lifecycle.ViewModel
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedCharacterViewModel : ViewModel() {
    private val _selectedCharacter = MutableStateFlow<MarvelCharacter?>(null)
    val selectedCharacter = _selectedCharacter.asStateFlow()

    fun onSelectCharacter(character: MarvelCharacter?) {
        _selectedCharacter.value = character
    }
}

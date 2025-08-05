package com.mlombardi.marvelcharacters.characters_list.presentation.detail

import androidx.lifecycle.ViewModel
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterDetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(CharacterDetailState())
    val state = _state.asStateFlow()

    private lateinit var selectedCharacter: MarvelCharacter

    fun selectCharacter(character: MarvelCharacter) {
        selectedCharacter = character
        _state.update {
            it.copy(
                character = selectedCharacter
            )
        }
    }

    fun onAction(action: CharacterDetailAction){

    }
}

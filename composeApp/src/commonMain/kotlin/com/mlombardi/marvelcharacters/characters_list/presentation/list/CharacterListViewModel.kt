package com.mlombardi.marvelcharacters.characters_list.presentation.list

import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.domain.usecases.GetCharactersUseCase
import com.mlombardi.marvelcharacters.core.domain.ResponseWrapper
import com.mlombardi.marvelcharacters.core.presentation.MarvelViewModel
import com.mlombardi.marvelcharacters.core.presentation.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class CharacterListViewModel(private val getCharactersUseCase: GetCharactersUseCase) : MarvelViewModel() {
    private val _state = MutableStateFlow(CharactersListState())
    val state = _state.asStateFlow()

    init {
        getCharacters(state.value.lastVisible)
    }

    fun onAction(action: CharactersListAction) {
        when (action) {
            is CharactersListAction.OnCharacterClicked -> {
            }

            is CharactersListAction.OnGoToComicsClicked -> {
            }

            is CharactersListAction.OnListScrolled -> {
                _state.update {
                    it.copy(
                        lastVisible = action.lastItemVisibleIndex
                    )
                }
                if (!state.value.isLoading && state.value.results.isNotEmpty()) {
                    getCharacters(action.lastItemVisibleIndex)
                }
            }
        }
    }

    private fun getCharacters(offset: Int) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        subscribeFlow(
            getCharactersUseCase.invoke(offset)
                .onEach { result ->
                    when (result) {
                        is ResponseWrapper.Error -> {
                            _state.update {
                                it.copy(
                                    errorMessage = UiText.DynamicString(result.error.toString()),
                                    isLoading = false
                                )
                            }
                        }

                        is ResponseWrapper.Success -> {
                            _state.update { state ->
                                val combined =
                                    (state.results + (result.data as List<MarvelCharacter>)).distinctBy { it.name }
                                state.copy(
                                    results = combined,
                                    isLoading = combined.isEmpty()
                                )
                            }
                        }
                    }
                }
        )
    }
}

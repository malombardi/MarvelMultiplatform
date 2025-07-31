package com.mlombardi.marvelcharacters.characters_list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlombardi.marvelcharacters.IODispatcher
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.domain.usecases.GetCharactersUseCase
import com.mlombardi.marvelcharacters.core.domain.ResponseWrapper
import com.mlombardi.marvelcharacters.core.presentation.UiText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

class CharacterListViewModel(private val getCharactersUseCase: GetCharactersUseCase) : ViewModel() {
    private val _state = MutableStateFlow(CharactersListState())
    val state = _state.asStateFlow()

    init {
        getCharacters()
    }

    fun OnAction(action: CharactersListAction) {
        _state.update {
            it.copy(isLoading = true)
        }
        getDetails((action as CharactersListAction.OnCharacterClicked).character)
    }

    private fun getCharacters() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        val charactersList: ArrayList<MarvelCharacter> = arrayListOf()
        subscribeFlow(
            getCharactersUseCase.invoke(0)
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
                            charactersList.addAll(result.data as List)
                            _state.update {
                                it.copy(
                                    results = charactersList,
                                    isLoading = false
                                )
                            }
                        }
                    }
                }
        )
    }

    fun <T> subscribeFlow(flow: Flow<T>) {
        flow.onStart {
        }.onCompletion {
        }.flowOn(IODispatcher)
            .launchIn(viewModelScope)
    }

    private fun getDetails(marvelCharacter: MarvelCharacter) {
        //after getting the info just put loading false
        _state.update {
            it.copy(isLoading = false)
        }
    }
}

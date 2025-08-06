package com.mlombardi.marvelcharacters.comics_list.presentation.list

import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.comics_list.domain.usecases.GetComicsUseCase
import com.mlombardi.marvelcharacters.core.domain.ResponseWrapper
import com.mlombardi.marvelcharacters.core.presentation.MarvelViewModel
import com.mlombardi.marvelcharacters.core.presentation.UiText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ComicListViewModel(private val getComicsUseCase: GetComicsUseCase) : MarvelViewModel() {
    private val _state = MutableStateFlow(ComicsListState())
    val state = _state.asStateFlow()

    init {
        getComics(state.value.lastVisible)
    }

    fun onAction(action: ComicsListAction) {
        when (action) {
            is ComicsListAction.OnComicClicked -> {
            }

            is ComicsListAction.OnGoToCharactersClicked -> {
            }

            is ComicsListAction.OnListScrolled -> {
                _state.update {
                    it.copy(
                        lastVisible = action.lastItemVisibleIndex
                    )
                }
                if (!state.value.isLoading && state.value.results.isNotEmpty()) {
                    getComics(action.lastItemVisibleIndex)
                }
            }
        }
    }

    private fun getComics(offset: Int) {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        subscribeFlow(
            getComicsUseCase.invoke(offset)
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
                                    (state.results + (result.data as List<MarvelComic>)).distinctBy { it.title }
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

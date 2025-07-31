package com.mlombardi.marvelcharacters.characters_list.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.presentation.composables.CharacterListView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharacterListScreenRoot(
    viewModel: CharacterListViewModel = koinViewModel(),
    onCharacterClicked: (MarvelCharacter) -> Unit
) {
    val state by viewModel.state.collectAsState()

    CharacterListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is CharactersListAction.OnCharacterClicked -> {
                    CharactersListAction.OnCharacterClicked(action.character)
                }

                else -> Unit
            }
            viewModel.OnAction(action)
        }
    )
}

@Composable
private fun CharacterListScreen(
    state: CharactersListState,
    onAction: (CharactersListAction) -> Unit,
) {
    CharacterListView(
        characters = state.results,
        onCharacterClicked = { onAction(CharactersListAction.OnCharacterClicked(it)) }
    )
}

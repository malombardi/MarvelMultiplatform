package com.mlombardi.marvelcharacters.characters_list.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mlombardi.marvelcharacters.characters_list.presentation.composables.CharacterDetailView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharacterDetailScreenRoot(
    viewModel: CharacterDetailViewModel = koinViewModel(),
    onCloseClicked: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    CharacterDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is CharacterDetailAction.OnCloseClicked -> {
                    onCloseClicked()
                }
            }
            viewModel.onAction(action)
        },

    )
}

@Composable
private fun CharacterDetailScreen(
    state: CharacterDetailState,
    onAction: (CharacterDetailAction) -> Unit,
) {
    CharacterDetailView(
        character = state.character,
        onCloseClicked = { onAction(CharacterDetailAction.OnCloseClicked) },
    )
}

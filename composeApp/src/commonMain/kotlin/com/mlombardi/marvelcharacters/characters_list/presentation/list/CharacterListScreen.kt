package com.mlombardi.marvelcharacters.characters_list.presentation.list

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.presentation.composables.CharacterListView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CharacterListScreenRoot(
    viewModel: CharacterListViewModel = koinViewModel(),
    onCharacterClicked: (MarvelCharacter) -> Unit
) {
    val state by viewModel.state.collectAsState()

    val scrollState = rememberLazyGridState()

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex to scrollState.layoutInfo.totalItemsCount }
            .collect { (firstVisible, total) ->
                val lastVisibleItemIndex = firstVisible + scrollState.layoutInfo.visibleItemsInfo.size
                if (lastVisibleItemIndex >= total) {
                    viewModel.onAction(CharactersListAction.OnListScrolled(lastVisibleItemIndex))
                }
            }
    }

    CharacterListScreen(
        state = state,
        scrollState = scrollState,
        onAction = { action ->
            when (action) {
                is CharactersListAction.OnCharacterClicked -> {
                    onCharacterClicked(action.character)
                }
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun CharacterListScreen(
    state: CharactersListState,
    scrollState: LazyGridState,
    onAction: (CharactersListAction) -> Unit,
) {
    CharacterListView(
        characters = state.results,
        onCharacterClicked = {
            onAction(CharactersListAction.OnCharacterClicked(it)) },
        scrollState = scrollState
    )
}

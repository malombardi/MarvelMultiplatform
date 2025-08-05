package com.mlombardi.marvelcharacters.characters_list.presentation.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter
import com.mlombardi.marvelcharacters.characters_list.presentation.composables.CharacterListView
import com.mlombardi.marvelcharacters.core.presentation.Red500
import com.mlombardi.marvelcharacters.core.presentation.Transparent
import com.mlombardi.marvelcharacters.core.presentation.composables.PulseAnimation
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
                val lastVisibleItemIndex =
                    firstVisible + scrollState.layoutInfo.visibleItemsInfo.size
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
    if (state.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PulseAnimation(modifier = Modifier.size(500.dp))
        }
    } else {
        CharacterListView(
            characters = state.results,
            onCharacterClicked = {
                onAction(CharactersListAction.OnCharacterClicked(it))
            },
            scrollState = scrollState
        )
    }
}

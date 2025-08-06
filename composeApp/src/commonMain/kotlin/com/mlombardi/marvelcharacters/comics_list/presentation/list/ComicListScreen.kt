package com.mlombardi.marvelcharacters.comics_list.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mlombardi.marvelcharacters.characters_list.presentation.composables.ComicListView
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic
import com.mlombardi.marvelcharacters.core.presentation.composables.PulseAnimation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComicListScreenRoot(
    viewModel: ComicListViewModel = koinViewModel(),
    onComicClicked: (MarvelComic) -> Unit,
    onGoToCharactersClicked: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    val scrollState = rememberLazyGridState()

    LaunchedEffect(scrollState) {
        snapshotFlow { scrollState.firstVisibleItemIndex to scrollState.layoutInfo.totalItemsCount }
            .collect { (firstVisible, total) ->
                val lastVisibleItemIndex =
                    firstVisible + scrollState.layoutInfo.visibleItemsInfo.size
                if (lastVisibleItemIndex >= total) {
                    viewModel.onAction(ComicsListAction.OnListScrolled(lastVisibleItemIndex))
                }
            }
    }

    ComicListScreen(
        state = state,
        scrollState = scrollState,
        onAction = { action ->
            when (action) {
                is ComicsListAction.OnComicClicked -> {
                    onComicClicked(action.comic)
                }

                is ComicsListAction.OnGoToCharactersClicked -> {
                    onGoToCharactersClicked()
                }

                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
private fun ComicListScreen(
    state: ComicsListState,
    scrollState: LazyGridState,
    onAction: (ComicsListAction) -> Unit,
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ComicListView(
            comics = state.results,
            onComicClicked = {
                onAction(ComicsListAction.OnComicClicked(it))
            },
            onGoToCharactersClicked = {
                onAction(ComicsListAction.OnGoToCharactersClicked)
            },
            scrollState = scrollState
        )
        if (state.isLoading) {
            PulseAnimation(modifier = Modifier.size(100.dp))
        }
    }

}

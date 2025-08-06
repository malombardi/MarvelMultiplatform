package com.mlombardi.marvelcharacters.comics_list.presentation.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.mlombardi.marvelcharacters.characters_list.presentation.composables.ComicDetailView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ComicDetailScreenRoot(
    viewModel: ComicDetailViewModel = koinViewModel(),
    onCloseClicked: () -> Unit
) {

    val state by viewModel.state.collectAsState()

    ComicDetailScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is ComicDetailAction.OnCloseClicked -> {
                    onCloseClicked()
                }
            }
        },
        )
}

@Composable
private fun ComicDetailScreen(
    state: ComicDetailState,
    onAction: (ComicDetailAction) -> Unit,
) {
    ComicDetailView(
        comic = state.comic,
        onCloseClicked = { onAction(ComicDetailAction.OnCloseClicked) },
    )
}

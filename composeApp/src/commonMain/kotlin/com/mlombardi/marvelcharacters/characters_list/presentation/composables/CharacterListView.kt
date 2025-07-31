package com.mlombardi.marvelcharacters.characters_list.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mlombardi.marvelcharacters.characters_list.domain.models.MarvelCharacter

@Composable
fun CharacterListView(
    characters: List<MarvelCharacter>,
    onCharacterClicked: (MarvelCharacter) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(
            items = characters,
            key = {
                it.id
            }
        ) { character ->
            CharacterListItemView(
                character = character,
                modifier = Modifier.widthIn(700.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { onCharacterClicked(character) }
            )
        }
    }
}

package com.mlombardi.marvelcharacters.characters_list.presentation.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mlombardi.marvelcharacters.comics_list.domain.models.MarvelComic

@Composable
fun ComicListView(
    comics: List<MarvelComic>,
    onComicClicked: (MarvelComic) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyGridState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            modifier = modifier,
            state = scrollState,
            columns = GridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(
                start = 16.dp,
                top = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            horizontalArrangement = Arrangement.Center,
            content = {
                items(comics.size) { index ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                    ) {
                        ComicListItemView(
                            comic = comics[index],
                            modifier = Modifier.padding(16.dp),
                            onClick = {
                                onComicClicked(comics[index])
                            }
                        )
                    }
                }
            }
        )
    }
}

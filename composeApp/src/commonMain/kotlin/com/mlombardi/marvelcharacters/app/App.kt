package com.mlombardi.marvelcharacters.app

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mlombardi.marvelcharacters.characters_list.presentation.SelectedCharacterViewModel
import com.mlombardi.marvelcharacters.characters_list.presentation.detail.CharacterDetailScreenRoot
import com.mlombardi.marvelcharacters.characters_list.presentation.detail.CharacterDetailViewModel
import com.mlombardi.marvelcharacters.characters_list.presentation.list.CharacterListScreenRoot
import com.mlombardi.marvelcharacters.characters_list.presentation.list.CharacterListViewModel
import com.mlombardi.marvelcharacters.comics_list.presentation.SelectedComicViewModel
import com.mlombardi.marvelcharacters.comics_list.presentation.detail.ComicDetailScreenRoot
import com.mlombardi.marvelcharacters.comics_list.presentation.detail.ComicDetailViewModel
import com.mlombardi.marvelcharacters.comics_list.presentation.list.ComicListScreenRoot
import com.mlombardi.marvelcharacters.comics_list.presentation.list.ComicListViewModel
import com.mlombardi.marvelcharacters.core.presentation.Red500
import com.mlombardi.marvelcharacters.core.presentation.Red800
import com.mlombardi.marvelcharacters.core.presentation.White
import marvelcharacters.composeapp.generated.resources.Res
import marvelcharacters.composeapp.generated.resources.characters
import marvelcharacters.composeapp.generated.resources.comics
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        val viewModel = koinViewModel<AppViewModel>()
        val state by viewModel.state.collectAsState()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.route.equals(Route.CHARACTERS_LIST.name)
                || destination.route.equals(Route.CHARACTER_DETAILS.name)
            ) {
                viewModel.onAction(AppAction.OnTabSelected(0))
            }
            if (destination.route.equals(Route.COMICS_LIST.name)
                || destination.route.equals(Route.COMICS_DETAILS.name)
            ) {
                viewModel.onAction(AppAction.OnTabSelected(1))
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(
                selectedTabIndex = state.selectedTabIndex,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .widthIn(max = 700.dp)
                    .fillMaxWidth(),
                containerColor = White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        color = Red500,
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                    )
                }
            ) {
                Tab(
                    selected = state.selectedTabIndex == 0,
                    onClick = {
                        viewModel.onAction(AppAction.OnTabSelected(0))
                        navController.navigate(Route.CHARACTERS_LIST.name)
                    },
                    modifier = Modifier,
                    selectedContentColor = Red800,
                    unselectedContentColor = Red500.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = stringResource(Res.string.characters),
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
                Tab(
                    selected = state.selectedTabIndex == 1,
                    onClick = {
                        viewModel.onAction(AppAction.OnTabSelected(1))
                        navController.navigate(Route.COMICS_LIST.name)
                    },
                    modifier = Modifier,
                    selectedContentColor = Red800,
                    unselectedContentColor = Red500.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = stringResource(Res.string.comics),
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))

            NavHost(navController, startDestination = Route.CHARACTERS_LIST.name) {

                composable(
                    Route.CHARACTERS_LIST.name,
                    exitTransition = { fadeOut() },
                    enterTransition = { fadeIn() }) {
                    val viewModel = koinViewModel<CharacterListViewModel>()
                    val selectedCharacterViewModel =
                        koinViewModel<SelectedCharacterViewModel>()

                    LaunchedEffect(true) {
                        selectedCharacterViewModel.onSelectCharacter(null)
                    }

                    CharacterListScreenRoot(
                        viewModel = viewModel,
                        onCharacterClicked = { character ->
                            selectedCharacterViewModel.onSelectCharacter(character)
                            navController.navigate(Route.CHARACTER_DETAILS.name)
                        }
                    )
                }
                composable(
                    Route.CHARACTER_DETAILS.name,
                    exitTransition = { fadeOut() },
                    enterTransition = { fadeIn() }) { it ->
                    val selectedCharacterViewModel =
                        koinViewModel<SelectedCharacterViewModel>()
                    val viewModel = koinViewModel<CharacterDetailViewModel>()
                    val selectedCharacter by selectedCharacterViewModel.selectedCharacter.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedCharacter) {
                        selectedCharacter?.let { character ->
                            viewModel.selectCharacter(character)
                        }
                    }

                    CharacterDetailScreenRoot(
                        viewModel = viewModel,
                        onCloseClicked = {
                            selectedCharacterViewModel.onSelectCharacter(null)
                            navController.navigate(Route.CHARACTERS_LIST.name)
                        },
                    )
                }

                composable(
                    Route.COMICS_LIST.name,
                    exitTransition = { fadeOut() },
                    enterTransition = { fadeIn() }) { it ->

                    val viewModel = koinViewModel<ComicListViewModel>()
                    val selectedComicViewModel =
                        koinViewModel<SelectedComicViewModel>()

                    LaunchedEffect(true) {
                        selectedComicViewModel.onSelectComic(null)
                    }

                    ComicListScreenRoot(
                        viewModel = viewModel,
                        onComicClicked = { comic ->
                            selectedComicViewModel.onSelectComic(comic)
                            navController.navigate(Route.COMICS_DETAILS.name)
                        },
                    )
                }
                composable(
                    Route.COMICS_DETAILS.name,
                    exitTransition = { fadeOut() },
                    enterTransition = { fadeIn() }) { it ->
                    val selectedComicViewModel =
                        koinViewModel<SelectedComicViewModel>()
                    val viewModel = koinViewModel<ComicDetailViewModel>()
                    val selectedComic by selectedComicViewModel.selectedComic.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedComic) {
                        selectedComic?.let { comic ->
                            viewModel.selectComic(comic)
                        }
                    }

                    ComicDetailScreenRoot(
                        viewModel = viewModel,
                        onCloseClicked = {
                            selectedComicViewModel.onSelectComic(null)
                            navController.navigate(Route.COMICS_LIST.name)
                        },
                    )
                }
            }
        }
    }
}

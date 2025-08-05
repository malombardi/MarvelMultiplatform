package com.mlombardi.marvelcharacters.app

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mlombardi.marvelcharacters.characters_list.presentation.SelectedCharacterViewModel
import com.mlombardi.marvelcharacters.characters_list.presentation.detail.CharacterDetailScreenRoot
import com.mlombardi.marvelcharacters.characters_list.presentation.detail.CharacterDetailViewModel
import com.mlombardi.marvelcharacters.characters_list.presentation.list.CharacterListScreenRoot
import com.mlombardi.marvelcharacters.characters_list.presentation.list.CharacterListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController, startDestination = Route.CHARACTERS_LIST.name) {
            composable(
                Route.CHARACTERS_LIST.name,
                exitTransition = { fadeOut() },
                popEnterTransition = { fadeIn() }) {
                val viewModel = koinViewModel<CharacterListViewModel>()
                val selectedCharacterViewModel =
                    it.sharedKoinViewModel<SelectedCharacterViewModel>(navController)

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
                popEnterTransition = { fadeIn() }) { it ->
                val selectedCharacterViewModel =
                    it.sharedKoinViewModel<SelectedCharacterViewModel>(navController)
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
        }
    }
}

@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}
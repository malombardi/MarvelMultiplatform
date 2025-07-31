package com.mlombardi.marvelcharacters.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.mlombardi.marvelcharacters.characters_list.presentation.CharacterListScreenRoot
import com.mlombardi.marvelcharacters.characters_list.presentation.CharacterListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    MaterialTheme {
        val viewModel = koinViewModel<CharacterListViewModel>()
        /*val selectedBookViewModel =
            it.sharedKoinViewModel<SelectedBookViewModel>(navController)

        LaunchedEffect(true) {
            selectedBookViewModel.onSelectBook(null)
        }
*/
        CharacterListScreenRoot(
            viewModel = viewModel,
            onCharacterClicked = { character ->
                {}
                /* selectedBookViewModel.onSelectBook(book)
                 navController.navigate(
                     Route.BookDetail(book.id)
                 )
                 */
            }
        )

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
package com.mlombardi.marvelcharacters.app

import com.mlombardi.marvelcharacters.core.presentation.MarvelViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : MarvelViewModel() {
    private val _state = MutableStateFlow(AppState(0))
    val state = _state.asStateFlow()

    fun onAction(action: AppAction) {
        when (action) {
            is AppAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.selectedTab)
                }
            }
        }
    }
}
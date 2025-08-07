package com.mlombardi.marvelcharacters.app

sealed interface AppAction {
    data class OnTabSelected(val selectedTab: Int) : AppAction
}

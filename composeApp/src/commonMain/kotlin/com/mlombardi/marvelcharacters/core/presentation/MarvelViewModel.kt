package com.mlombardi.marvelcharacters.core.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mlombardi.marvelcharacters.IODispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

open class MarvelViewModel: ViewModel() {

    fun <T> subscribeFlow(flow: Flow<T>) {
        flow.onStart {
        }.onCompletion {
        }.flowOn(IODispatcher)
            .launchIn(viewModelScope)
    }
}
package com.words.presentation.base.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiState, UiEvent, UiSideEffect>(initialState: UiState) : ViewModel() {
    private val _uiState = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _sideEffect: Channel<UiSideEffect> = Channel()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val currentState: UiState
        get() = _uiState.value

    open fun setEvent(event: UiEvent) {
        dispatchEvent(event)
    }

    fun dispatchEvent(event: UiEvent) = viewModelScope.launch {
        handleEvent(event)
    }

    protected abstract suspend fun handleEvent(event: UiEvent)

    protected fun setState(reduce: UiState.() -> UiState) {
        val state = currentState.reduce()
        _uiState.value = state
    }

    protected fun setEffect(vararg builder: UiSideEffect) {
        for (effectValue in builder) {
            viewModelScope.launch { _sideEffect.send(effectValue) }
        }
    }
}

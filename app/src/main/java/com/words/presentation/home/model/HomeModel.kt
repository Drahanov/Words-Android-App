package com.words.presentation.home.model

import com.words.domain.words.model.WordEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState

class HomeModel {
    data class HomeUiState(
        val isEmpty: Boolean = true,
        val isLoading: Boolean = false,
        val words: List<WordEntity> = listOf()
    ) : UiState

    sealed class HomeUiEvent : UiEvent

    sealed class HomeUiSideEffect : UiSideEffect
}
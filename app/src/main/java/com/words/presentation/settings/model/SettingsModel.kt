package com.words.presentation.settings.model

import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState

class SettingsModel {
    class SettingsUiState: UiState
    sealed class SettingsUiEvent: UiEvent
    sealed class SettingsUiEffect: UiSideEffect
}
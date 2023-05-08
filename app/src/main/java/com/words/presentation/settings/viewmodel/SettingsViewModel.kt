package com.words.presentation.settings.viewmodel

import com.words.presentation.base.viewmodel.BaseViewModel
import com.words.presentation.settings.model.SettingsModel.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(): BaseViewModel<SettingsUiState, SettingsUiEvent, SettingsUiEffect>(
    SettingsUiState()
) {
    override suspend fun handleEvent(event: SettingsUiEvent) {

    }
}
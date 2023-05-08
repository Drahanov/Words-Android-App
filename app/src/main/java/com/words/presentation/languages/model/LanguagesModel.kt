package com.words.presentation.languages.model

import com.words.domain.languages.model.LanguageEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState


class LanguagesModel {
    data class LanguagesUiState(
        val languages: List<LanguageEntity> = listOf(),
        val selectedLanguageCode: String = ""
    ) : UiState

    sealed class LanguageUiEvent : UiEvent {
    }

    class LanguageUiEffect : UiSideEffect
}
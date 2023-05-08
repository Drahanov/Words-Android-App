package com.words.presentation.targets.model

import com.words.domain.languages.model.LanguageEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState

class TargetsModel {
    data class TargetsUiState(
        val languages: List<LanguageEntity> = listOf(),
        val kostql: Boolean = false,
        val mode: Mode = Mode.NATIVE,
    ) : UiState

    sealed class TargetsUiEvent : UiEvent {
        data class LanguageSelected(val id: Int) : TargetsUiEvent()
        object Confirmed : TargetsUiEvent()
    }

    sealed class TargetsUiEffect : UiSideEffect {
        object SelectItemUiEffect : TargetsUiEffect()
        object LanguagesSelected : TargetsUiEffect()
    }
}

enum class Mode {
    NATIVE,
    TARGETS
}
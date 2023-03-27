package com.words.presentation.newWord.model

import com.words.domain.words.model.WordEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState

class NewWordModel {
    data class NewWordUiState(
        val isLoading: Boolean = false,
        val word: String = "",
        val translation: String = ""
    ) : UiState

    sealed class NewWordUiEvent : UiEvent {
        class AddNewWord(val word: WordEntity) : NewWordUiEvent()
        class WordTyped(val word: String) : NewWordUiEvent()
    }

    class NewWordUiSideEffect : UiSideEffect
}
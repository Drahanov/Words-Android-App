package com.words.presentation.question.model

import com.words.domain.category.model.CategoryEntity
import com.words.domain.words.model.WordEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState
import com.words.presentation.newWord.model.NewWordModel

class QuestionModel {
    data class QuestionUiState(
        val categories: List<CategoryEntity> = listOf(),
        val words: List<WordEntity> = listOf(),
        val round: Int = 0
    ) : UiState

    sealed class QuestionUiEvent : UiEvent {
        object Selected : QuestionUiEvent()
        object SaveNewQuiz : QuestionUiEvent()
    }

    sealed class QuestionUiSideEffect : UiSideEffect {
        object ShowResult : QuestionUiSideEffect()
    }
}
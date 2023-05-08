package com.words.presentation.quiz.model

import com.words.domain.category.model.CategoryEntity
import com.words.domain.words.model.WordEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState
import com.words.presentation.newWord.model.NewWordModel

class QuizModel {
    data class QuizUiState(
        val categories: List<CategoryEntity> = listOf(),
        val kostql: Boolean = false,
        ) : UiState

    sealed class QuizUiEvent : UiEvent {
        data class CategorySelected(public val category: CategoryEntity) : QuizUiEvent()
    }

    sealed class QuizUiSideEffect : UiSideEffect
}
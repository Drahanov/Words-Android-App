package com.words.presentation.statistics.model

import com.words.domain.words.model.WordEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState

class StatisticsModel {
    data class StatisticsUiState(
        val newWordsToday: Int = 0,
        val learnedToday: Int = 0,
        val percentOfLearned: Int = 0,
        val words: List<WordEntity> = listOf()
    ) : UiState

    sealed class StatisticsUiEvent : UiEvent
    sealed class StatisticsUiEffect : UiSideEffect
}
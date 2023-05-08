package com.words.presentation.home.model

import com.words.domain.category.model.CategoryEntity
import com.words.domain.words.model.WordEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState
import com.words.presentation.newWord.model.NewWordModel

class HomeModel {
    data class HomeUiState(
        val isLoading: Boolean = false,
        val words: List<WordEntity> = listOf(),
        val categories: List<CategoryEntity> = listOf(),
        val selectedCategories: MutableList<Int> = mutableListOf(0),
        val searchKeyword: String = "",
    ) : UiState

    sealed class HomeUiEvent : UiEvent {
        class RemoveWord(val word: WordEntity) : HomeUiEvent()
        data class ClickCategory(val id: Int) : HomeUiEvent()
        data class SearchChange(val keyword: String) : HomeUiEvent()
        data class WordLearned(val word: WordEntity, val isChecked: Boolean) : HomeUiEvent()
    }

    sealed class HomeUiSideEffect : UiSideEffect
}
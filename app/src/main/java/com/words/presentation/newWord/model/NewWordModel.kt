package com.words.presentation.newWord.model

import com.words.domain.category.model.CategoryEntity
import com.words.domain.languages.model.LanguageEntity
import com.words.domain.words.model.WordEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState

class NewWordModel {
    data class NewWordUiState(
        val isLoading: Boolean = false,
        val word: String = "",
        val translation: String = "",
        val categories: MutableList<CategoryEntity> = mutableListOf(),
        val isError: Boolean = false,
        val selectedCategory: Int = 0,
        val selectedLanguage: String = "",
        val nativeLanguage: List<LanguageEntity> = listOf(),
        val studiedLanguages: List<LanguageEntity> = listOf()
    ) : UiState

    sealed class NewWordUiEvent : UiEvent {
        object AddNewWord : NewWordUiEvent()
        class WordTyped(val word: String) : NewWordUiEvent()
        class CategorySelected(val categoryId: Int) : NewWordUiEvent()
        class LanguageSelected(val languageCode: String) : NewWordUiEvent()
    }

    class NewWordUiSideEffect : UiSideEffect
}
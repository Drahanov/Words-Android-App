package com.words.presentation.categories.model

import com.words.domain.category.model.CategoryEntity
import com.words.presentation.base.model.UiEvent
import com.words.presentation.base.model.UiSideEffect
import com.words.presentation.base.model.UiState

class CategoriesModel {
    data class CategoriesUiState(
        val categories: List<CategoryEntity> = listOf(),
        val colors: List<Long> = listOf(0xFF272838, 0xFFF3DE8A, 0xFF7E7F9A, 0xFFEB9486, 0xFFF9F8F8)
    ) : UiState

    sealed class CategoriesUiEvent : UiEvent {
        data class UpdateCategory(val category: CategoryEntity) : CategoriesUiEvent()
        data class AddNewCategory(val category: CategoryEntity) : CategoriesUiEvent()
        data class DeleteNewCategory(val category: CategoryEntity) : CategoriesUiEvent()
    }

    class CategoriesUiEffect : UiSideEffect
}
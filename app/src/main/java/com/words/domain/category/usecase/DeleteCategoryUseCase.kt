package com.words.domain.category.usecase

import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.repository.CategoriesRepository

class DeleteCategoryUseCase(private val repository: CategoriesRepository) {
    suspend fun invoke(category: CategoryEntity) {
        repository.deleteCategory(category)
    }
}
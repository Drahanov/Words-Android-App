package com.words.domain.category.usecase

import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.repository.CategoriesRepository

class AddCategoryUseCase(private val repository: CategoriesRepository) {
    suspend fun invoke(category: CategoryEntity) {
        repository.addCategory(category)
    }
}
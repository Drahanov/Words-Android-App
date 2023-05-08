package com.words.domain.category.usecase

import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow

class ReadAllCategoriesUseCase(private val repository: CategoriesRepository) {
    suspend fun invoke(): Flow<List<CategoryEntity>> {
        return repository.readAllCategories()
    }
}
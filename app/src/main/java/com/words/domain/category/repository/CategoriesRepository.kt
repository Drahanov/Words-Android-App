package com.words.domain.category.repository

import com.words.domain.category.model.CategoryEntity
import com.words.domain.words.model.WordEntity
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun readAllCategories(): Flow<List<CategoryEntity>>
    suspend fun addCategory(category: CategoryEntity)
    suspend fun deleteCategory(category: CategoryEntity)
    suspend fun updateCategory(category: CategoryEntity)
}
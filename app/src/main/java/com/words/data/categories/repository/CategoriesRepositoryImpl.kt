package com.words.data.categories.repository

import com.words.data.categories.dao.CategoriesDao
import com.words.domain.category.model.CategoryEntity
import com.words.domain.category.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CategoriesRepositoryImpl @Inject constructor(private val categoriesDao: CategoriesDao) :
    CategoriesRepository {
    override suspend fun readAllCategories(): Flow<List<CategoryEntity>> {
        return categoriesDao.readAllCategories()
    }

    override suspend fun addCategory(category: CategoryEntity) {
        categoriesDao.addCategory(category)
    }

    override suspend fun deleteCategory(category: CategoryEntity) {
        categoriesDao.deleteCategory(category = category)
    }

    override suspend fun updateCategory(category: CategoryEntity) {
        categoriesDao.updateCategory(category = category)
    }
}
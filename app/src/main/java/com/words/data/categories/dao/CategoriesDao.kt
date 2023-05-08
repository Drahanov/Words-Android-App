package com.words.data.categories.dao

import androidx.room.*
import com.words.domain.category.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Insert
    suspend fun addCategory(category: CategoryEntity)

    @Update
    suspend fun updateCategory(category: CategoryEntity)

    @Delete
    suspend fun deleteCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories_table ORDER BY id ASC")
    fun readAllCategories(): Flow<List<CategoryEntity>>
}
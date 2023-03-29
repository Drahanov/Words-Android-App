package com.words.data.categories.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.words.domain.category.model.CategoryEntity
import com.words.domain.words.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {
    @Insert
    suspend fun addCategory(category: CategoryEntity)

    @Query("SELECT * FROM categories_table ORDER BY id ASC")
    fun readAllCategories(): Flow<List<CategoryEntity>>
}
package com.words.data.languages.dao

import androidx.room.*
import com.words.domain.category.model.CategoryEntity
import com.words.domain.languages.model.LanguageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguagesDao {
    @Query("SELECT * FROM languages_table ORDER BY id ASC")
    fun readAllLanguages(): Flow<List<LanguageEntity>>

    @Update
    suspend fun updateLanguage(language: LanguageEntity)

    @Query("SELECT * FROM languages_table WHERE isNative = 1")
    fun readNativeLanguage(): Flow<List<LanguageEntity>>

    @Query("SELECT * FROM languages_table WHERE languages_table.isStudied = 1")
    fun readStudiedLanguages(): Flow<List<LanguageEntity>>
}
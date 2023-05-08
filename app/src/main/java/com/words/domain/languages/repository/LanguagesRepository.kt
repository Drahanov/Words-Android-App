package com.words.domain.languages.repository

import com.words.domain.languages.model.LanguageEntity
import kotlinx.coroutines.flow.Flow

interface LanguagesRepository {
    suspend fun readAllLanguages(): Flow<List<LanguageEntity>>

    suspend fun updateLanguage(lang: LanguageEntity)

    suspend fun getNativeLanguage(): Flow<List<LanguageEntity>>

    suspend fun getStudiedLanguage(): Flow<List<LanguageEntity>>

}
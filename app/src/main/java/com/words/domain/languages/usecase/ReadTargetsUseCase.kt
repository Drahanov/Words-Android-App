package com.words.domain.languages.usecase

import com.words.domain.languages.model.LanguageEntity
import com.words.domain.languages.repository.LanguagesRepository
import kotlinx.coroutines.flow.Flow

class ReadTargetsUseCase(private val repository: LanguagesRepository) {
    suspend fun invoke(): Flow<List<LanguageEntity>> {
        return repository.getStudiedLanguage()
    }
}
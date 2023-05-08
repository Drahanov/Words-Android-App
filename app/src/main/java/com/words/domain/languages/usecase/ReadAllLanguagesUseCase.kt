package com.words.domain.languages.usecase

import com.words.domain.languages.model.LanguageEntity
import com.words.domain.languages.repository.LanguagesRepository
import com.words.domain.words.model.WordEntity
import com.words.domain.words.repository.WordsRepository
import kotlinx.coroutines.flow.Flow

class ReadAllLanguagesUseCase(private val repository: LanguagesRepository) {
    suspend fun invoke(): Flow<List<LanguageEntity>> {
        return repository.readAllLanguages()
    }
}
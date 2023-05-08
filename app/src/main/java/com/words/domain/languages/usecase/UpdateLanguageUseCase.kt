package com.words.domain.languages.usecase

import com.words.domain.languages.model.LanguageEntity
import com.words.domain.languages.repository.LanguagesRepository

class UpdateLanguageUseCase(private val repository: LanguagesRepository) {
    suspend fun invoke(category: LanguageEntity) {
        repository.updateLanguage(category)
    }
}
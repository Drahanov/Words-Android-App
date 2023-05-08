package com.words.domain.words.usecase

import com.words.domain.words.model.TranslationEntity
import com.words.domain.words.repository.TranslationRepository
import retrofit2.Call

class TranslateWordUseCase(private val repository: TranslationRepository) {
    suspend fun invoke(word: String, lang1: String, lang2: String): Call<TranslationEntity> {
        return repository.translateWord(word, "$lang1|$lang2")
    }
}
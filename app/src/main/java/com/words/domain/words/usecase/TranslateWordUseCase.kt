package com.words.domain.words.usecase

import com.words.domain.words.model.ResponseWordModel
import com.words.domain.words.repository.TranslationRepository
import retrofit2.Call

class TranslateWordUseCase(private val repository: TranslationRepository) {
    suspend fun invoke(word: String): Call<ResponseWordModel> {
        return repository.translateWord(word)
    }
}
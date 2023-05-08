package com.words.domain.words.repository

import com.words.domain.words.model.TranslationEntity
import retrofit2.Call

interface TranslationRepository {
    suspend fun translateWord(word: String, langpair: String): Call<TranslationEntity>
}
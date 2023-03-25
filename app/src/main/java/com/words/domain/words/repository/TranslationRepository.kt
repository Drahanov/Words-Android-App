package com.words.domain.words.repository

import com.words.domain.words.model.WordEntity
import retrofit2.Call

interface TranslationRepository {
    suspend fun translateWord(word: String): Call<WordEntity>
}
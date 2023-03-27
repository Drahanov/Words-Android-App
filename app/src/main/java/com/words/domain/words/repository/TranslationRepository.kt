package com.words.domain.words.repository

import com.words.domain.words.model.ResponseWordModel
import retrofit2.Call

interface TranslationRepository {
    suspend fun translateWord(word: String): Call<ResponseWordModel>
}
package com.words.data.words.repository

import com.words.data.words.api.TranslationApi
import com.words.domain.words.model.WordEntity
import com.words.domain.words.repository.TranslationRepository
import retrofit2.Call

class TranslationRepositoryImpl(private val translationApi: TranslationApi) :
    TranslationRepository {
    override suspend fun translateWord(word: String): Call<WordEntity> {
        return translationApi.getTranslation(langpair = "en|it", q = word)
    }
}
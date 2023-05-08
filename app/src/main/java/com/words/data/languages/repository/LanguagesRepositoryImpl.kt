package com.words.data.languages.repository

import com.words.data.languages.dao.LanguagesDao
import com.words.domain.languages.model.LanguageEntity
import com.words.domain.languages.repository.LanguagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LanguagesRepositoryImpl @Inject constructor(private val languagesDao: LanguagesDao) :
    LanguagesRepository {

    override suspend fun readAllLanguages(): Flow<List<LanguageEntity>> {
        return languagesDao.readAllLanguages()
    }

    override suspend fun updateLanguage(lang: LanguageEntity) {
        languagesDao.updateLanguage(lang)
    }

    override suspend fun getNativeLanguage(): Flow<List<LanguageEntity>> {
        return languagesDao.readNativeLanguage()
    }

    override suspend fun getStudiedLanguage(): Flow<List<LanguageEntity>> {
        return languagesDao.readStudiedLanguages()
    }
}

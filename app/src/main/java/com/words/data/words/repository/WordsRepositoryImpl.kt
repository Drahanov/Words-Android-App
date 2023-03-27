package com.words.data.words.repository

import androidx.lifecycle.LiveData
import com.words.data.words.dao.WordsDao
import com.words.domain.words.model.WordEntity
import com.words.domain.words.repository.WordsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WordsRepositoryImpl @Inject constructor(private val wordDao: WordsDao) : WordsRepository {
    override suspend fun readAllWords(): Flow<List<WordEntity>> {
        return wordDao.readAllWords()
    }

    override suspend fun addWord(word: WordEntity) {
        wordDao.addWord(word)
    }
}
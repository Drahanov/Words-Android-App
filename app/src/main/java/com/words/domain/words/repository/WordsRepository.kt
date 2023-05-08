package com.words.domain.words.repository

import com.words.domain.words.model.WordEntity
import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun readAllWords(): Flow<List<WordEntity>>
    suspend fun addWord(word: WordEntity)
    suspend fun removeWord(word: WordEntity)
    suspend fun updateWord(word: WordEntity)
    suspend fun getWordsToday(date: Long) : Flow<List<WordEntity>>
}
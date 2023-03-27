package com.words.domain.words.repository

import com.words.domain.words.model.WordEntity
import kotlinx.coroutines.flow.Flow

interface WordsRepository {

    suspend fun readAllWords(): Flow<List<WordEntity>>
    suspend fun addWord(word: WordEntity)
}
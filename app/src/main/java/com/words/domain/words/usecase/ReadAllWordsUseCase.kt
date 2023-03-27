package com.words.domain.words.usecase

import com.words.domain.words.model.WordEntity
import com.words.domain.words.repository.WordsRepository
import kotlinx.coroutines.flow.Flow

class ReadAllWordsUseCase(private val repository: WordsRepository) {
    suspend fun invoke(): Flow<List<WordEntity>> {
        return repository.readAllWords()
    }
}
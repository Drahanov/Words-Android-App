package com.words.domain.words.usecase

import com.words.domain.words.model.WordEntity
import com.words.domain.words.repository.WordsRepository

class LearnWordUseCase(private val repository: WordsRepository) {
    suspend fun invoke(wordEntity: WordEntity) {
        return repository.updateWord(wordEntity)
    }
}
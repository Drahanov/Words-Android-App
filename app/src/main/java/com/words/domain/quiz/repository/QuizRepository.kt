package com.words.domain.quiz.repository

import com.words.domain.quiz.model.QuizEntity
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    suspend fun readAllQuizes(): Flow<List<QuizEntity>>
    suspend fun addNewQuiz(quiz: QuizEntity)
}
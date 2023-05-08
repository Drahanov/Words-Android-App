package com.words.data.quiz.repository

import com.words.data.quiz.dao.QuizDao
import com.words.domain.quiz.model.QuizEntity
import com.words.domain.quiz.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(private val dao: QuizDao) : QuizRepository {
    override suspend fun readAllQuizes(): Flow<List<QuizEntity>> {
        return dao.readAllQuizes()
    }

    override suspend fun addNewQuiz(quiz: QuizEntity) {
        dao.addQuiz(quiz)
    }
}
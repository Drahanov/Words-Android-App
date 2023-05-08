package com.words.data.quiz.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.words.domain.quiz.model.QuizEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuizDao {
    @Query("SELECT * FROM quiz_table ORDER BY id ASC")
    fun readAllQuizes(): Flow<List<QuizEntity>>

    @Insert
    suspend fun addQuiz(quiz: QuizEntity)
}
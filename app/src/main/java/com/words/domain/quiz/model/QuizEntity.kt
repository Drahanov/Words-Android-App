package com.words.domain.quiz.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "quiz_table")
data class QuizEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val date: Date,
    val wordsAmount: Int,
    val result: Int
)

package com.words.domain.words.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId: Int,
    val languageId: Int,
    val word: String,
    val translation: String
)
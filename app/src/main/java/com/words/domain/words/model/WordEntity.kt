package com.words.domain.words.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.util.*

@Entity(tableName = "words_table")
data class WordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val categoryId: Int,
    val languageId: Int,
    val langEmoji: String,
    val word: String,
    val translation: String,
    val addDate: Date,
    var learnDate: Date? = null,
    var isLearned: Boolean = false
)
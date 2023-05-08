package com.words.domain.languages.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "languages_table")
data class LanguageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val code: String,
    val title: String,
    val emojiCode: String,
    var isNative: Boolean,
    var isStudied: Boolean,
    val isHaveTranslation: Boolean
)
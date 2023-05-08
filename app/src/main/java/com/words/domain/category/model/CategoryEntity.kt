package com.words.domain.category.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val colorHex: Long,
    var isSelectedForQuiz: Boolean = false
)
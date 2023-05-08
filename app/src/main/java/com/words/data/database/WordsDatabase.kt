package com.words.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.words.data.categories.dao.CategoriesDao
import com.words.data.languages.dao.LanguagesDao
import com.words.data.quiz.dao.QuizDao
import com.words.data.words.dao.WordsDao
import com.words.domain.category.model.CategoryEntity
import com.words.domain.languages.model.LanguageEntity
import com.words.domain.quiz.model.QuizEntity
import com.words.domain.words.model.DateConvertor
import com.words.domain.words.model.WordEntity

@TypeConverters(DateConvertor::class)
@Database(entities = [WordEntity::class, CategoryEntity::class, LanguageEntity::class, QuizEntity::class], version = 1, exportSchema = true)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
    abstract fun categoriesDao(): CategoriesDao
    abstract fun languagesDao(): LanguagesDao
    abstract fun quizDao(): QuizDao
}
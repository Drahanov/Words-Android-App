package com.words.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.words.data.words.dao.WordsDao
import com.words.domain.words.model.WordEntity


@Database(entities = [WordEntity::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}
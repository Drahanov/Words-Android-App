package com.words.data.words.dao

import androidx.room.*
import com.words.domain.words.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Insert
    suspend fun addWord(word: WordEntity)

    @Delete
    suspend fun removeWord(word: WordEntity)

    @Update
    suspend fun updateWord(word: WordEntity)

    @Query("SELECT * FROM words_table ORDER BY id ASC")
    fun readAllWords(): Flow<List<WordEntity>>

    @Query("SELECT * FROM words_table where addDate = :date")
    fun getWordsToday(date: Long): Flow<List<WordEntity>>
}
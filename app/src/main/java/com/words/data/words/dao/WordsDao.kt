package com.words.data.words.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.words.domain.words.model.WordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Insert
    suspend fun addWord(word: WordEntity)

    @Query("SELECT * FROM words_table ORDER BY id ASC")
    fun readAllWords(): Flow<List<WordEntity>>
}
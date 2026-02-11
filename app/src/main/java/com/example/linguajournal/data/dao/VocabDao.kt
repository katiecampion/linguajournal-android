package com.example.linguajournal.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.linguajournal.data.entities.VocabEntity

@Dao
interface VocabDao {

    @Query("SELECT * FROM vocab ORDER BY id DESC")
    fun getAllVocab(): LiveData<List<VocabEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: VocabEntity)

    @Delete
    suspend fun deleteWord(word: VocabEntity)
}

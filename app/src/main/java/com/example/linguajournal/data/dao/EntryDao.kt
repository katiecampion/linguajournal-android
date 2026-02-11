package com.example.linguajournal.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.linguajournal.data.entities.EntryEntity

@Dao
interface EntryDao {

    @Query("SELECT * FROM entries ORDER BY date DESC")
    fun getAllEntries(): LiveData<List<EntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: EntryEntity)

    @Update
    suspend fun update(entry: EntryEntity)

    @Delete
    suspend fun delete(entry: EntryEntity)
}

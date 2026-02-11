package com.example.linguajournal.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.linguajournal.data.dao.EntryDao
import com.example.linguajournal.data.dao.VocabDao
import com.example.linguajournal.data.entities.EntryEntity
import com.example.linguajournal.data.entities.VocabEntity

@Database(
    entities = [
        EntryEntity::class,
        VocabEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao
    abstract fun vocabDao(): VocabDao   // ← MISSING — ADD THIS

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lingua_journal_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

package com.example.linguajournal.repository

import com.example.linguajournal.data.dao.EntryDao
import com.example.linguajournal.data.entities.EntryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EntryRepository(private val entryDao: EntryDao) {

    val allEntries = entryDao.getAllEntries()

    suspend fun insert(entry: EntryEntity) {
        withContext(Dispatchers.IO) {
            entryDao.insert(entry)
        }
    }

    suspend fun update(entry: EntryEntity) {
        withContext(Dispatchers.IO) {
            entryDao.update(entry)
        }
    }

    suspend fun delete(entry: EntryEntity) {
        withContext(Dispatchers.IO) {
            entryDao.delete(entry)
        }
    }
}

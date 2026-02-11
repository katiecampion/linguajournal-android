package com.example.linguajournal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.linguajournal.data.database.AppDatabase
import com.example.linguajournal.data.entities.EntryEntity
import com.example.linguajournal.repository.EntryRepository
import kotlinx.coroutines.launch

class EntryViewModel(application: Application) : AndroidViewModel(application) {

    private val entryDao = AppDatabase.getDatabase(application).entryDao()
    private val repository = EntryRepository(entryDao)

    val allEntries = repository.allEntries

    fun insert(entry: EntryEntity) = viewModelScope.launch {
        repository.insert(entry)
    }

    fun update(entry: EntryEntity) = viewModelScope.launch {
        repository.update(entry)
    }

    fun delete(entry: EntryEntity) = viewModelScope.launch {
        repository.delete(entry)
    }
}

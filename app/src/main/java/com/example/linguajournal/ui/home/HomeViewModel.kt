package com.example.linguajournal.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.linguajournal.data.database.AppDatabase
import com.example.linguajournal.data.entities.EntryEntity

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val entryDao = AppDatabase.getDatabase(application).entryDao()

    val entries: LiveData<List<EntryEntity>> = entryDao.getAllEntries()
}

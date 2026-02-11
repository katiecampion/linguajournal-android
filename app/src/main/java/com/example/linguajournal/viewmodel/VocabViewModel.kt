package com.example.linguajournal.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.linguajournal.data.database.AppDatabase
import com.example.linguajournal.data.entities.VocabEntity
import com.example.linguajournal.data.repository.VocabRepository
import kotlinx.coroutines.launch

class VocabViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: VocabRepository
    val allVocab: LiveData<List<VocabEntity>>

    init {
        val dao = AppDatabase.getDatabase(application).vocabDao()
        repository = VocabRepository(dao)
        allVocab = repository.allVocab
    }

    fun insert(word: VocabEntity) {
        viewModelScope.launch {
            repository.insert(word)
        }
    }

    fun delete(word: VocabEntity) {
        viewModelScope.launch {
            repository.delete(word)
        }
    }
}

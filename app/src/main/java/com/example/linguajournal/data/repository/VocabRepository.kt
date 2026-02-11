package com.example.linguajournal.data.repository

import androidx.lifecycle.LiveData
import com.example.linguajournal.data.dao.VocabDao
import com.example.linguajournal.data.entities.VocabEntity

class VocabRepository(private val dao: VocabDao) {

    val allVocab: LiveData<List<VocabEntity>> = dao.getAllVocab()

    suspend fun insert(word: VocabEntity) {
        dao.insertWord(word)
    }

    suspend fun delete(word: VocabEntity) {
        dao.deleteWord(word)
    }
}

package com.example.linguajournal.utils

object VocabExtractor {


    fun extractWords(text: String): List<String> {
        return text
            .lowercase()
            .replace("[^\\p{L} ]+".toRegex(), "")   // keep unicode letters only
            .split(" ")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .distinct()
    }
}

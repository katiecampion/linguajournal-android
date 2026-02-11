package com.example.linguajournal.utils

object LanguageHelper {

    fun isNativeWord(word: String, nativeLang: String): Boolean {
        return word.matches(Regex("^[A-Za-z]+$")) && nativeLang == "en"
    }
}

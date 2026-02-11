package com.example.linguajournal.api

data class TranslationRequest(
    val text: String,
    val fromLang: String,
    val toLang: String
)

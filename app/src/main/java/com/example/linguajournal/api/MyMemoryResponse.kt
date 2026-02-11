package com.example.linguajournal.api

data class MyMemoryResponse(
    val responseData: ResponseData
)

data class ResponseData(
    val translatedText: String
)

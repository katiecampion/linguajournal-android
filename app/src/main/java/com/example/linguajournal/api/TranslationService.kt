package com.example.linguajournal.api

import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationService {

    @GET("get")
    suspend fun translate(
        @Query("q") text: String,
        @Query("langpair") langPair: String
    ): MyMemoryResponse
}

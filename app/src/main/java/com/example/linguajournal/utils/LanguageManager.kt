package com.example.linguajournal.utils

import android.content.Context
import android.content.SharedPreferences

object LanguageManager {

    private const val PREFS = "lang_prefs"
    private const val NATIVE = "native_lang"
    private const val LEARNING = "learn_lang"

    private fun prefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)


    fun setNativeLanguage(context: Context, code: String) {
        prefs(context).edit().putString(NATIVE, code.lowercase()).apply()
    }

    fun setLearningLanguage(context: Context, code: String) {
        prefs(context).edit().putString(LEARNING, code.lowercase()).apply()
    }


    fun getNativeLanguage(context: Context): String =
        prefs(context).getString(NATIVE, "en") ?: "en"

    fun getLearningLanguage(context: Context): String =
        prefs(context).getString(LEARNING, "es") ?: "es"
}

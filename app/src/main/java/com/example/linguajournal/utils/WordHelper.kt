package com.example.linguajournal.utils

object WordHelper {

    fun getWordAtCursor(text: String, cursor: Int): String? {
        if (cursor < 0 || cursor > text.length) return null
        if (text.isEmpty()) return null

        var start = cursor
        var end = cursor

        while (start > 0 && text[start - 1].isLetter()) start--
        while (end < text.length && text[end].isLetter()) end++

        if (start == end) return null

        return text.substring(start, end)
    }

    fun getWordBounds(text: String, cursor: Int, word: String): Pair<Int, Int>? {
        val index = text.indexOf(word, ignoreCase = true)
        if (index == -1) return null
        return index to (index + word.length)
    }
}

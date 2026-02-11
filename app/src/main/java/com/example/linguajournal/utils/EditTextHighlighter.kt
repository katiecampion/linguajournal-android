package com.example.linguajournal.utils

import android.widget.EditText

object EditTextHighlighter {

    fun getSelectedText(editText: EditText): String? {
        val start = editText.selectionStart
        val end = editText.selectionEnd

        return if (start >= 0 && end > start) {
            editText.text.substring(start, end)
        } else null
    }
}

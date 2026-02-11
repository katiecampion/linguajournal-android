package com.example.linguajournal.ui.translation

import android.app.Dialog
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.linguajournal.databinding.BottomsheetTranslationBinding

class TranslationBottomSheet(
    private val originalWord: String,
    private val translatedWord: String,
    private val onReplace: (String) -> Unit,
    private val onSaveVocab: (String, String) -> Unit
) : BottomSheetDialogFragment() {

    private var _binding: BottomsheetTranslationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        _binding = BottomsheetTranslationBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)

        binding.wordOriginal.text = originalWord
        binding.wordTranslated.text = translatedWord

        binding.btnReplace.setOnClickListener {
            onReplace(translatedWord)
            dismiss()
        }

        binding.btnVocab.setOnClickListener {
            onSaveVocab(originalWord, translatedWord)
            dismiss()
        }

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

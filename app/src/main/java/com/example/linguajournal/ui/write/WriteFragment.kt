package com.example.linguajournal.ui.write

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.linguajournal.data.database.AppDatabase
import com.example.linguajournal.data.entities.EntryEntity
import com.example.linguajournal.data.entities.VocabEntity
import com.example.linguajournal.databinding.FragmentWriteBinding
import com.example.linguajournal.ui.translation.TranslationBottomSheet
import com.example.linguajournal.api.*
import com.example.linguajournal.utils.LanguageManager
import kotlinx.coroutines.launch

class WriteFragment : Fragment() {

    private var _binding: FragmentWriteBinding? = null
    private val binding get() = _binding!!
    private val args: WriteFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entryDao = AppDatabase.getDatabase(requireContext()).entryDao()


        args.entry?.let {
            binding.inputTitle.setText(it.title)
            binding.inputContent.setText(it.content)
        }


        binding.btnSave.setOnClickListener {
            val entry = EntryEntity(
                id = args.entry?.id ?: 0,
                title = binding.inputTitle.text.toString(),
                content = binding.inputContent.text.toString(),
                date = System.currentTimeMillis()
            )

            lifecycleScope.launch {
                if (args.entry == null) entryDao.insert(entry)
                else entryDao.update(entry)

                findNavController().popBackStack()
            }
        }


        binding.inputContent.setOnLongClickListener {
            val edit = binding.inputContent
            val selected = edit.text.substring(
                edit.selectionStart.coerceAtLeast(0),
                edit.selectionEnd.coerceAtLeast(0)
            )

            if (selected.isNotEmpty()) translateWord(selected)
            true
        }


        binding.writeToolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun translateWord(word: String) {
        val from = LanguageManager.getNativeLanguage(requireContext())
        val to = LanguageManager.getLearningLanguage(requireContext())
        val langPair = "$from|$to"

        val service = RetrofitClient.instance.create(TranslationService::class.java)

        lifecycleScope.launch {
            try {
                Log.d("TRANSLATE", "Translating '$word' using $langPair")

                val response = service.translate(word, langPair)
                val translated = response.responseData.translatedText

                openTranslationSheet(word, translated)

            } catch (e: Exception) {
                Log.e("TRANSLATE_ERROR", "Translation failed: ${e.message}", e)
                openTranslationSheet(word, "$word (?)")
            }
        }
    }

    private fun openTranslationSheet(original: String, translated: String) {
        val sheet = TranslationBottomSheet(
            originalWord = original,
            translatedWord = translated,
            onReplace = { newWord -> replaceSelectedText(newWord) },
            onSaveVocab = { orig, trans -> saveToVocab(orig, trans) }
        )
        sheet.show(parentFragmentManager, "translator")
    }

    private fun replaceSelectedText(newText: String) {
        val edit = binding.inputContent
        val start = edit.selectionStart
        val end = edit.selectionEnd

        if (start >= 0 && end >= 0 && start != end) {
            edit.text.replace(start, end, newText)
        }
    }

    private fun saveToVocab(original: String, translated: String) {
        val vocabDao = AppDatabase.getDatabase(requireContext()).vocabDao()

        lifecycleScope.launch {
            vocabDao.insertWord(
                VocabEntity(word = original, translation = translated)
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

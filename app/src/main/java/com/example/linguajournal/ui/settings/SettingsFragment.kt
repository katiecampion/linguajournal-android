package com.example.linguajournal.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.linguajournal.databinding.FragmentSettingsBinding
import com.example.linguajournal.utils.LanguageManager

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    // Supported language ISO codes for the API
    private val languageCodes = listOf("en", "es", "fr", "de", "it", "pt")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLanguageSpinners()
        loadSavedLanguages()

        binding.btnSaveSettings.setOnClickListener {
            saveLanguages()
        }
    }

    private fun setupLanguageSpinners() {
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            languageCodes
        )

        binding.spinnerNative.adapter = adapter
        binding.spinnerLearning.adapter = adapter
    }

    private fun loadSavedLanguages() {
        val native = LanguageManager.getNativeLanguage(requireContext())
        val learning = LanguageManager.getLearningLanguage(requireContext())

        binding.spinnerNative.setSelection(languageCodes.indexOf(native))
        binding.spinnerLearning.setSelection(languageCodes.indexOf(learning))
    }

    private fun saveLanguages() {
        val native = binding.spinnerNative.selectedItem.toString()
        val learning = binding.spinnerLearning.selectedItem.toString()

        LanguageManager.setNativeLanguage(requireContext(), native)
        LanguageManager.setLearningLanguage(requireContext(), learning)

        requireActivity().onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

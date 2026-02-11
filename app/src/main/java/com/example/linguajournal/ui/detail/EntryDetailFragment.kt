package com.example.linguajournal.ui.detail

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.linguajournal.data.database.AppDatabase
import com.example.linguajournal.data.entities.EntryEntity
import com.example.linguajournal.databinding.FragmentEntryDetailBinding
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

class EntryDetailFragment : Fragment() {

    private var _binding: FragmentEntryDetailBinding? = null
    private val binding get() = _binding!!
    private val args: EntryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEntryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entry = args.entry
        val dao = AppDatabase.getDatabase(requireContext()).entryDao()

        binding.detailTitle.text = entry.title
        binding.detailContent.text = entry.content
        binding.detailDate.text =
            android.text.format.DateFormat.format("dd MMM yyyy", entry.date)

        // Back button
        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        // Edit
        binding.btnEdit.setOnClickListener {
            val action = EntryDetailFragmentDirections.actionEntryDetailToWrite(entry)
            findNavController().navigate(action)
        }

        // Delete
        binding.btnDelete.setOnClickListener {
            lifecycleScope.launch {
                dao.delete(entry)
                findNavController().navigateUp()
            }
        }

        // SHARE
        binding.btnShare.setOnClickListener {
            shareEntry(entry)
        }

    }

    private fun shareEntry(entry: EntryEntity) {
        val text = "📘 ${entry.title}\n\n${entry.content}"

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        }

        startActivity(Intent.createChooser(intent, "Share Entry"))
    }

    private fun exportEntry(entry: EntryEntity) {
        try {
            val fileName = "${entry.title.replace(" ", "_")}.txt"
            val downloads = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val file = File(downloads, fileName)

            FileOutputStream(file).use {
                it.write(entry.content.toByteArray())
            }

            val uri = FileProvider.getUriForFile(
                requireContext(),
                "${requireContext().packageName}.provider",
                file
            )

            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "text/plain")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            Toast.makeText(requireContext(), "Saved to Downloads", Toast.LENGTH_SHORT).show()

            startActivity(intent)

        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Error exporting file", Toast.LENGTH_SHORT).show()
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

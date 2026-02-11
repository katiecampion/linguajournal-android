package com.example.linguajournal.ui.vocab

import android.os.Bundle
import android.view.*
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.linguajournal.adapters.VocabAdapter
import com.example.linguajournal.data.entities.VocabEntity
import com.example.linguajournal.databinding.FragmentVocabBinding
import com.example.linguajournal.viewmodel.VocabViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class VocabFragment : Fragment() {

    private var _binding: FragmentVocabBinding? = null
    private val binding get() = _binding!!

    private lateinit var vocabViewModel: VocabViewModel
    private lateinit var adapter: VocabAdapter

    private var fullList: List<VocabEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVocabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vocabViewModel = ViewModelProvider(requireActivity())[VocabViewModel::class.java]

        adapter = VocabAdapter()
        binding.vocabRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.vocabRecyclerView.adapter = adapter

        // Swipe delete
        val swipeToDelete = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.getItemAt(vh.adapterPosition)
                vocabViewModel.delete(item)
            }
        }
        ItemTouchHelper(swipeToDelete).attachToRecyclerView(binding.vocabRecyclerView)

        // Observe DB
        vocabViewModel.allVocab.observe(viewLifecycleOwner) { list ->
            fullList = list
            adapter.submitList(list)
            binding.vocabEmptyState.visibility =
                if (list.isEmpty()) View.VISIBLE else View.GONE
        }

        // Search
        binding.searchInput.addTextChangedListener { text ->
            val query = text?.toString()?.lowercase().orEmpty()
            val filtered = fullList.filter {
                it.word.lowercase().contains(query) ||
                        it.translation.lowercase().contains(query)
            }
            adapter.submitList(filtered)
        }

        // Sort menu
        binding.sortIcon.setOnClickListener { showSortMenu() }

        // Add vocabulary
        binding.btnAddVocab.setOnClickListener { showAddVocabDialog() }

        // Fix FAB being cut off by gesture nav bar
        ViewCompat.setOnApplyWindowInsetsListener(binding.btnAddVocab) { fab, insets ->
            val bottomInset = insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            fab.translationY = -bottomInset.toFloat()
            return@setOnApplyWindowInsetsListener insets
        }
    }

    private fun showSortMenu() {
        val options = arrayOf("A → Z", "Z → A", "Newest First", "Oldest First")

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Sort Vocabulary")
            .setItems(options) { _, which ->
                val sorted = when (which) {
                    0 -> fullList.sortedBy { it.word.lowercase() }
                    1 -> fullList.sortedByDescending { it.word.lowercase() }
                    2 -> fullList.sortedByDescending { it.id }
                    3 -> fullList.sortedBy { it.id }
                    else -> fullList
                }
                adapter.submitList(sorted)
            }
            .show()
    }

    private fun showAddVocabDialog() {
        val wordInput = android.widget.EditText(requireContext()).apply { hint = "Word" }
        val transInput = android.widget.EditText(requireContext()).apply { hint = "Translation" }

        val container = android.widget.LinearLayout(requireContext()).apply {
            orientation = android.widget.LinearLayout.VERTICAL
            setPadding(40, 20, 40, 20)
            addView(wordInput)
            addView(transInput)
        }

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Add Vocabulary")
            .setView(container)
            .setPositiveButton("Save") { _, _ ->
                val w = wordInput.text.toString()
                val t = transInput.text.toString()
                if (w.isNotBlank() && t.isNotBlank()) {
                    vocabViewModel.insert(VocabEntity(word = w, translation = t))
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

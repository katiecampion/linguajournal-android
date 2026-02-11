package com.example.linguajournal.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.linguajournal.adapters.EntryAdapter
import com.example.linguajournal.data.database.AppDatabase
import com.example.linguajournal.data.entities.EntryEntity
import com.example.linguajournal.databinding.FragmentHomeBinding
import com.example.linguajournal.ui.home.HomeFragmentDirections

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: EntryAdapter

    private var newestFirst = true
    private var currentList: List<EntryEntity> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EntryAdapter { entry ->
            val action = HomeFragmentDirections.actionHomeToEntryDetail(entry)
            findNavController().navigate(action)
        }

        binding.recyclerEntries.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerEntries.adapter = adapter

        val entryDao = AppDatabase.getDatabase(requireContext()).entryDao()

        entryDao.getAllEntries().observe(viewLifecycleOwner, Observer { list ->
            currentList = list
            updateEmptyState()
            applySort()
        })


        // Empty state "Write your first entry"
        binding.btnEmptyAddEntry.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToWrite(null)
            findNavController().navigate(action)
        }

        // Sort toggle
        binding.btnSort.setOnClickListener {
            newestFirst = !newestFirst
            applySort()
        }
    }

    private fun updateEmptyState() {
        val isEmpty = currentList.isEmpty()

        binding.homeEmptyState.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.btnEmptyAddEntry.visibility = if (isEmpty) View.VISIBLE else View.GONE
        binding.recyclerEntries.visibility = if (isEmpty) View.GONE else View.VISIBLE
    }

    private fun applySort() {
        val sorted = if (newestFirst) {
            currentList.sortedByDescending { it.date }
        } else {
            currentList.sortedBy { it.date }
        }
        adapter.submitList(sorted)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

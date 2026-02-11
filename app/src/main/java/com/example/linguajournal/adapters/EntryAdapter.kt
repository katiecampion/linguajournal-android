package com.example.linguajournal.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.linguajournal.data.entities.EntryEntity
import com.example.linguajournal.databinding.ItemEntryBinding

class EntryAdapter(
    private val onClick: (EntryEntity) -> Unit
) : ListAdapter<EntryEntity, EntryAdapter.EntryViewHolder>(DiffCallback()) {

    inner class EntryViewHolder(private val binding: ItemEntryBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: EntryEntity) {

            binding.entryTitle.text = entry.title
            binding.entryDate.text = android.text.format.DateFormat.format("dd MMM yyyy", entry.date)

            binding.root.setOnClickListener {
                onClick(entry)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val binding = ItemEntryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return EntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<EntryEntity>() {
        override fun areItemsTheSame(old: EntryEntity, new: EntryEntity): Boolean =
            old.id == new.id

        override fun areContentsTheSame(old: EntryEntity, new: EntryEntity): Boolean =
            old == new
    }
}

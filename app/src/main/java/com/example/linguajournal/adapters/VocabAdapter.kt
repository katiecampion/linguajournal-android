package com.example.linguajournal.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.linguajournal.data.entities.VocabEntity
import com.example.linguajournal.databinding.ItemVocabBinding

class VocabAdapter : RecyclerView.Adapter<VocabAdapter.VocabViewHolder>() {

    private var vocabList: List<VocabEntity> = emptyList()

    var onDelete: ((VocabEntity) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabViewHolder {
        val binding = ItemVocabBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VocabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VocabViewHolder, position: Int) {
        holder.bind(vocabList[position])
    }

    override fun getItemCount() = vocabList.size

    fun submitList(list: List<VocabEntity>) {
        vocabList = list
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): VocabEntity = vocabList[position]

    inner class VocabViewHolder(private val binding: ItemVocabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: VocabEntity) {
            binding.vocabWord.text = item.word
            binding.vocabTranslation.text = item.translation

            binding.deleteVocabBtn.setOnClickListener {
                onDelete?.invoke(item)
            }
        }
    }
}

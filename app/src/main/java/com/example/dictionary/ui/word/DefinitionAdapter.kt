package com.example.dictionary.ui.word

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.data.model.DefinitionsModel
import com.example.dictionary.databinding.DefinitionItemBinding


class DefinitionAdapter: ListAdapter<DefinitionsModel, DefinitionAdapter.DefinitionViewHolder>(
    DefinitionDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DefinitionItemBinding.inflate(inflater, parent, false)
        return DefinitionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        val currentDefinition = getItem(position)

        holder.bind(currentDefinition)
    }

    class DefinitionViewHolder(private val binding: DefinitionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: DefinitionsModel) {
            binding.tvDefinition.text = item.definition
            binding.tvExample.text = item.example ?: ""

        }
    }
}

class DefinitionDiffCallback : DiffUtil.ItemCallback<DefinitionsModel>() {
    override fun areItemsTheSame(oldItem: DefinitionsModel, newItem: DefinitionsModel): Boolean {
        return oldItem.definition == newItem.definition
    }

    override fun areContentsTheSame(oldItem: DefinitionsModel, newItem: DefinitionsModel): Boolean {
        return oldItem == newItem
    }
}
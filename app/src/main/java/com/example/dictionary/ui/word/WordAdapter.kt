package com.example.dictionary.ui.word

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.data.model.MeaningsModel
import com.example.dictionary.databinding.WordItemBinding
import com.example.dictionary.ui.searchedwords.SearchedWordsAdapter

class WordAdapter(): RecyclerView.Adapter<WordViewHolder>() {

    private val items = ArrayList<MeaningsModel>()


    fun setItems(items: ArrayList<MeaningsModel>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding: WordItemBinding = WordItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) = holder.bind(items[position])
}

class WordViewHolder(private val itemBinding: WordItemBinding) : RecyclerView.ViewHolder(itemBinding.root)
     {

    private lateinit var word: MeaningsModel


    @SuppressLint("SetTextI18n")
    fun bind(item: MeaningsModel) {
        this.word = item
        itemBinding.tvPartOfSpeech.text = item.partOfSpeech
        val definitionAdapter = DefinitionAdapter()
        definitionAdapter.submitList(item.definitions)
        val context = itemBinding.root.context
        itemBinding.rvDefinition.layoutManager = LinearLayoutManager(context)
        itemBinding.rvDefinition.adapter = definitionAdapter

       // itemBinding.tvDefinition.text = item.definitions.joinToString("\n") { it.definition }


    }
}


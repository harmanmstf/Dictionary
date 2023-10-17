package com.example.dictionary.ui.searchedwords

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.data.model.SearchedWord
import com.example.dictionary.databinding.SearchedWordItemBinding


class SearchedWordsAdapter(
    private val onItemClicked: (SearchedWord) -> Unit
) : ListAdapter<SearchedWord, SearchedWordsAdapter.SearchedWordsViewHolder>(
    FeedingDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchedWordsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchedWordItemBinding.inflate(inflater, parent, false)
        return SearchedWordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchedWordsViewHolder, position: Int) {
        val currentFeeding = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(currentFeeding)
        }
        holder.bind(currentFeeding)
    }

    class SearchedWordsViewHolder(private val binding: SearchedWordItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(searchedWord: SearchedWord) {
            binding.tvSearchedWord.text = searchedWord.searchedWord

        }
    }
}

class FeedingDiffCallback : DiffUtil.ItemCallback<SearchedWord>() {
    override fun areItemsTheSame(oldItem: SearchedWord, newItem: SearchedWord): Boolean {
        return oldItem.searchedWord == newItem.searchedWord
    }

    override fun areContentsTheSame(oldItem: SearchedWord, newItem: SearchedWord): Boolean {
        return oldItem == newItem
    }
}


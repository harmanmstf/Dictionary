package com.example.dictionary.ui.searchedwords

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dictionary.databinding.FragmentSearchedWordsBinding
import com.example.dictionary.ui.word.WordViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchedWordsFragment : Fragment() {

    private var _binding: FragmentSearchedWordsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WordViewModel by activityViewModels()
    private lateinit var adapter: SearchedWordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchedWordsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupObserver()
    }


    private fun setupRecyclerView() {
        val numberOfColumns = 2
        adapter = SearchedWordsAdapter { }
        val layoutManager = GridLayoutManager(requireContext(), numberOfColumns)

        binding.rvSearchedWord.layoutManager = layoutManager
        binding.rvSearchedWord.adapter = adapter
    }

    private fun setupObserver() {
        viewModel.searchedWords.observe(viewLifecycleOwner) { searchedWords ->
            searchedWords?.let {
                adapter.submitList(searchedWords)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.dictionary.ui.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.dictionary.databinding.FragmentWordBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.utils.Resource

@AndroidEntryPoint
class WordFragment : Fragment() {

    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!
    private val viewModel: WordViewModel by viewModels()
    private lateinit var adapter: WordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupRecyclerView()


        binding.btnSearch.setOnClickListener {
          viewModel.search(word = binding.etSearch.text.toString())
        }
    }

    private fun setupRecyclerView() {
        adapter = WordAdapter()
        binding.rvDefinition.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDefinition.adapter = adapter

    }

    private fun setupObservers() {
        viewModel.results.observe(viewLifecycleOwner) { it ->
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { word ->
                        binding.tvWord.text = word.first().word
                        binding.tvPhoneticText.text = word.first().phonetics.joinToString("\n") {
                            it.text
                        }
                        adapter.setItems(ArrayList(it.data.map { it.meanings }.flatten()))

                        binding.pbData.isVisible = false

                        viewModel.saveWord(word.first().word)
                    }
                }
                Resource.Status.LOADING -> binding.pbData.isVisible = true
                Resource.Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                    binding.pbData.isVisible = false
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
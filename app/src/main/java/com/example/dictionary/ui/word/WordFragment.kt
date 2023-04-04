package com.example.dictionary.ui.word

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentWordBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dictionary.utils.Resource

@AndroidEntryPoint
class WordFragment : Fragment() {

    private var _binding: FragmentWordBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WordViewModel by viewModels()


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

        binding.btnSearch.setOnClickListener {
          viewModel.start(word = binding.etSearch.text.toString())
        }
    }

    private fun setupObservers() {
        viewModel.results?.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Resource.Status.SUCCESS -> {
                    it.data?.let { d ->
                        binding.tvWord.text =d.word
                        binding.tvPhonetic.text =d.phonetic
                    }
                }
                Resource.Status.ERROR -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                Resource.Status.LOADING -> { }
            }
        })
    }


}
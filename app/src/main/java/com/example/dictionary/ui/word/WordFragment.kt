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
          viewModel.search(word = binding.etSearch.text.toString())
        }
    }

    private fun setupObservers() {
        viewModel.results.observe(viewLifecycleOwner, Observer {
            when (it.status){
                Resource.Status.SUCCESS -> {
                    it.data?.let { d ->
                        binding.tvWord.text =d.first().word
                        binding.tvPhoneticText.text = d.first().phonetics.joinToString("\n") {
                            it.text
                        }
                        binding.tvPartOfSpeech.text = d.first().meanings.joinToString("\n" ) { it.partOfSpeech }
                        binding.tvDefinition.text = d.first().meanings.joinToString("\n") {it.definitions.first().definition }
                        binding.tvExample.text = d.first().meanings.joinToString("\n") { it.definitions.first().example }


                        binding.pbData.isVisible = false
                    }
                }
                Resource.Status.LOADING -> binding.pbData.isVisible = true
                Resource.Status.ERROR -> Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
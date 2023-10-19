package com.example.dictionary.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dictionary.R
import com.example.dictionary.data.local.Levels
import com.example.dictionary.databinding.FragmentLevelBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LevelFragment : Fragment() {

    private var _binding: FragmentLevelBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentLevelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnA1.setOnClickListener {
        viewModel._selectedLevel.value = Levels.A1
            navigateToGameFragment()
        }

        binding.btnA2.setOnClickListener {
            viewModel._selectedLevel.value = Levels.A2
            navigateToGameFragment()
        }

        binding.btnB1.setOnClickListener {
            viewModel._selectedLevel.value = Levels.B1
            navigateToGameFragment()
        }

        binding.btnB2.setOnClickListener {
            viewModel._selectedLevel.value = Levels.B2
            navigateToGameFragment()
        }
    }

    private fun navigateToGameFragment() {
        findNavController().navigate(R.id.action_homeFragment_to_gameFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.example.dictionary.ui.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.dictionary.R
import com.example.dictionary.databinding.FragmentGameBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

/**
 * Fragment where the game is played, contains the game logic.
 */
@AndroidEntryPoint
class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.score.observe(viewLifecycleOwner) { score ->
            binding.score.text = "Score: ${score}"
        }

        viewModel.currentWordCount.observe(viewLifecycleOwner) { currentWordCount ->
            binding.wordCount.text = " ${currentWordCount}of ${10} words"

        }

        viewModel.currentScrambledWord.observe(viewLifecycleOwner) { scrambledWord ->
            binding.textViewUnscrambledWord.text = scrambledWord.toString()
        }


        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        if (viewModel.isGameOver()) { showFinalScoreDialog() }

        viewModel.getNextWord()
    }

    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if (viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (!viewModel.nextWord()) {
                showFinalScoreDialog()
            }
        } else {
            setErrorTextField(true)
        }
    }


    private fun onSkipWord() {
        if (viewModel.nextWord()) {
            setErrorTextField(false)
        } else {
            showFinalScoreDialog()
        }
    }



    private var scoreDialog: AlertDialog? = null

    private fun showFinalScoreDialog() {
        val scoreObserver = Observer<Int> { score ->
            scoreDialog =  MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.congratulations))
                .setMessage(getString(R.string.you_scored, score))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.exit)) { _, _ ->
                    exitGame()
                }
                .setPositiveButton(getString(R.string.play_again)) { _, _ ->
                    restartGame()
                    scoreDialog?.dismiss()
                }
                .show()
        }

        viewModel.score.observe(viewLifecycleOwner, scoreObserver)
    }


    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeData()
        setErrorTextField(false)
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }
}

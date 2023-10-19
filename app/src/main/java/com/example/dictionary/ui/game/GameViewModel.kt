package com.example.dictionary.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.dictionary.data.local.Levels
import com.example.dictionary.data.local.MAX_NO_OF_WORDS
import com.example.dictionary.data.local.SCORE_INCREASE
import com.example.dictionary.data.local.a1List
import com.example.dictionary.data.local.a2List
import com.example.dictionary.data.local.b1List
import com.example.dictionary.data.local.b2List
import com.example.dictionary.data.model.SearchedWord
import com.example.dictionary.data.repository.WordRepository
import com.example.dictionary.ui.word.WordViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class GameViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {



    private val _score = MutableLiveData(0)
        val score: LiveData<Int>
            get() = _score

        private val _currentWordCount = MutableLiveData(0)
        val currentWordCount: LiveData<Int>
            get() = _currentWordCount

        private val _currentScrambledWord = MutableLiveData<String>()
        val currentScrambledWord: LiveData<Spannable> = Transformations.map(_currentScrambledWord) {
            if (it == null) {
                SpannableString("")
            } else {
                val scrambledWord = it.toString()
                val spannable: Spannable = SpannableString(scrambledWord)
                spannable.setSpan(
                    TtsSpan.VerbatimBuilder(scrambledWord).build(),
                    0,
                    scrambledWord.length,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE
                )
                spannable
            }
        }

        private var wordsList: MutableList<String> = mutableListOf()
        private lateinit var currentWord: String



    private var isGameOver: Boolean = false




    val _selectedLevel = MutableLiveData<Levels?>()
    private var selectedLevel: LiveData<Levels?> = _selectedLevel

    fun getNextWord() {
        val currentList = when(selectedLevel.value){
            Levels.A1 -> a1List
            Levels.A2 -> a2List
            Levels.B1 -> b1List
            Levels.B2 -> b2List
            null -> emptyList()
        }
        //val currentList = a1List
        if (currentList.isNotEmpty()) currentWord = currentList.random() else return

        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            Log.d("Unscramble", "currentWord= $currentWord")
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = _currentWordCount.value?.inc()
            wordsList.add(currentWord)
        }
    }

        /*
         * Re-initializes the game data to restart the game.
         */
        fun reinitializeData() {
            _score.value = 0
            _currentWordCount.value = 0
            wordsList.clear()
            getNextWord()
            isGameOver = false
        }

        /*
        * Increases the game score if the player’s word is correct.
        */
        private fun increaseScore() {
            _score.value = _score.value?.plus(SCORE_INCREASE)
        }

        /*
        * Returns true if the player word is correct.
        * Increases the score accordingly.
        */
        fun isUserWordCorrect(playerWord: String): Boolean {
            if (playerWord.equals(currentWord, true)) {
                increaseScore()
                return true
            }
            return false
        }

        /*
        * Returns true if the current word count is less than MAX_NO_OF_WORDS
        */
        fun nextWord(): Boolean {
            return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
                getNextWord()
                true
            } else {
                isGameOver = true
                false
            }
        }

        fun isGameOver() = isGameOver
    }

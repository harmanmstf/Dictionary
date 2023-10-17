package com.example.dictionary.ui.word

import androidx.lifecycle.*
import com.example.dictionary.data.model.SearchedWord
import com.example.dictionary.data.repository.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    private val _word = MutableLiveData<String>()

    private val _result = _word.switchMap { id ->
        repository.getWord(id)
    }

    val results = _result

    fun search(word: String) {
        _word.value = word
    }

    fun saveWord(word: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val searchedWord = SearchedWord(searchedWord = word)
            repository.insertWord(searchedWord)
        }
    }

    private val _searchedWords = repository.getWords()
    val searchedWords: LiveData<List<SearchedWord>> = _searchedWords
}
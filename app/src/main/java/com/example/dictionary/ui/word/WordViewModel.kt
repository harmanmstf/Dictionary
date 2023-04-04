package com.example.dictionary.ui.word

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.dictionary.data.model.WordModel
import com.example.dictionary.data.repository.WordRepository
import com.example.dictionary.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WordViewModel @Inject constructor(
    private val repository: WordRepository
) : ViewModel() {

    var results : LiveData<Resource<WordModel>>? = null

    fun start(word:String){
        results = repository.getWord(word)
        val aaa = results
        val vvv = results?.value
    }
}
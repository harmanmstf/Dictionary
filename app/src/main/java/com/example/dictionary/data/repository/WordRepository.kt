package com.example.dictionary.data.repository

import androidx.lifecycle.LiveData
import com.example.dictionary.data.local.WordDao
import com.example.dictionary.data.model.SearchedWord
import com.example.dictionary.data.model.WordModel
import com.example.dictionary.data.remote.WordRemoteDataSource
import com.example.dictionary.utils.performGetOperation
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val remoteDataSource: WordRemoteDataSource,
    private val localDataSource: WordDao
) {

    fun getWord(word: String) = performGetOperation(
        networkCall = { remoteDataSource.getWord(word) }
    )

    suspend fun insertWord(word: SearchedWord) {
        localDataSource.insertWord(word)
    }

    fun getWords(): LiveData<List<SearchedWord>> {
        return localDataSource.getWords()
    }
}
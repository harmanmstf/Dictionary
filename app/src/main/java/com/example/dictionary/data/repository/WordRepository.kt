package com.example.dictionary.data.repository

import com.example.dictionary.data.remote.WordRemoteDataSource
import com.example.dictionary.utils.performGetOperation
import javax.inject.Inject

class WordRepository @Inject constructor(
    private val remoteDataSource: WordRemoteDataSource,
) {

    fun getWord(word: String) = performGetOperation(
        networkCall = {
            remoteDataSource.getWord(word)
        }
    )
}
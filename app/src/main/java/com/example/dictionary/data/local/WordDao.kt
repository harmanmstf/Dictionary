package com.example.dictionary.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.model.SearchedWord
import com.example.dictionary.data.model.WordModel

@Dao
interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: SearchedWord)

    @Query("SELECT * FROM searchedWords")
    fun getWords(): LiveData<List<SearchedWord>>

}
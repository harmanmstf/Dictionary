package com.example.dictionary.data.remote

import com.example.dictionary.data.model.WordModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface  WordService{
    @GET("entries/en/{word}")
    suspend fun getWord(@Path("word") word: String): Response<WordModel>
}
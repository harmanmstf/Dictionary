package com.example.dictionary.di

import com.example.dictionary.data.remote.WordRemoteDataSource
import com.example.dictionary.data.remote.WordService
import com.example.dictionary.data.repository.WordRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.converter.gson.GsonConverterFactory


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.dictionaryapi.dev/api/v2/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideWordService(retrofit: Retrofit): WordService = retrofit.create(
        WordService::class.java)

    @Singleton
    @Provides
    fun provideWordRemoteDataSource(wordService: WordService) = WordRemoteDataSource(wordService)

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: WordRemoteDataSource) = WordRepository(remoteDataSource)
}
package com.example.dictionary.di

import android.content.Context
import com.example.dictionary.data.local.WordDao
import com.example.dictionary.data.local.WordDatabase
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
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideRepository(remoteDataSource: WordRemoteDataSource, localDataSource: WordDao) = WordRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        WordDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideWordDao(db: WordDatabase) = db.itemDao()
}
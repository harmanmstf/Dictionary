package com.example.dictionary.data.model

data class WordModel(
    val word: String,
    val phonetics: List<PhoneticsModel>,
    val meanings: List<MeaningsModel>
)

data class PhoneticsModel(
    val text: String,
)

data class  MeaningsModel(
    val partOfSpeech: String,
    val definitions: List<DefinitionsModel>
)

data class DefinitionsModel(
    val definition: String
)


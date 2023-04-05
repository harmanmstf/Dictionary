package com.example.dictionary.data.model

data class WordModel(
    val word: String,
    val phonetics: List<PhoneticsModel>,
    val meanings: List<MeaningsModel>

)

data class PhoneticsModel(
    val text: String,
    val audio: String
)

data class  MeaningsModel(
    val partOfSpeech: String,
    val definitions: List<DefinitionsModel>,
    val synonyms: List<String>,
    val antonyms: List<String>
)

data class DefinitionsModel(
    val definition: String,
    val example: String
)


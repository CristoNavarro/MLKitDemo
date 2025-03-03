package com.example.mlkitdemo.domain.repository

import com.example.mlkitdemo.domain.models.LanguageRecognitionResult

interface NaturalLanguageRepository {
    suspend fun getLanguageFrom(text: String): List<LanguageRecognitionResult>
    suspend fun getTranslation(text: String): String
}
package com.example.mlkitdemo.data.mappers

import com.example.mlkitdemo.domain.models.LanguageRecognitionResult
import com.google.mlkit.nl.languageid.IdentifiedLanguage

fun List<IdentifiedLanguage>.toLanguageRecognitionResult(): List<LanguageRecognitionResult> {
    return map { LanguageRecognitionResult(languageId = it.languageTag, confidence = it.confidence) }
}
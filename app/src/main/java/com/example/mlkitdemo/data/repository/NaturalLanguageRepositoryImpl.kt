package com.example.mlkitdemo.data.repository

import com.example.mlkitdemo.data.mappers.toLanguageRecognitionResult
import com.example.mlkitdemo.domain.models.LanguageRecognitionResult
import com.example.mlkitdemo.domain.repository.NaturalLanguageRepository
import com.google.android.gms.tasks.Tasks
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class NaturalLanguageRepositoryImpl: NaturalLanguageRepository {
    override suspend fun getLanguageFrom(text: String): List<LanguageRecognitionResult> {
        val languageIdentifier = LanguageIdentification.getClient()
        val task = languageIdentifier.identifyPossibleLanguages(text)
        Tasks.await(task)
        return task.result.toLanguageRecognitionResult()
    }

    override suspend fun getTranslation(text: String): String {
        var result = ""
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.SPANISH)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        val translator = Translation.getClient(options)
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()

        val modelTask = translator.downloadModelIfNeeded(conditions)
        Tasks.await(modelTask)

        if (modelTask.isSuccessful) {
            val task = translator.translate(text)
            Tasks.await(task)
            result = task.result
        }

        translator.close()
        return result
    }
}
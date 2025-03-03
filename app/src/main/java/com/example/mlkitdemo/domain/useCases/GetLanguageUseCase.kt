package com.example.mlkitdemo.domain.useCases

import com.example.mlkitdemo.domain.models.LanguageRecognitionResult
import com.example.mlkitdemo.domain.repository.NaturalLanguageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetLanguageUseCase(private val repository: NaturalLanguageRepository) {
    suspend operator fun invoke(text: String): List<LanguageRecognitionResult> {
        val scope = CoroutineScope(Dispatchers.IO)
        return scope.async {
            repository.getLanguageFrom(text)
        }.await()
    }
}
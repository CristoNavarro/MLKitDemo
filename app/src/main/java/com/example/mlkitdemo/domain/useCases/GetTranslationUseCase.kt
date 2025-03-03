package com.example.mlkitdemo.domain.useCases

import com.example.mlkitdemo.domain.repository.NaturalLanguageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class GetTranslationUseCase(private val repository: NaturalLanguageRepository) {
    suspend operator fun invoke(text: String): String {
        val scope = CoroutineScope(Dispatchers.IO)
        return scope.async {
            repository.getTranslation(text)
        }.await()
    }
}
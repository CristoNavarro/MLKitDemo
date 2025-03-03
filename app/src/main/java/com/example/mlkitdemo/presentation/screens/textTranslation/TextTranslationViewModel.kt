package com.example.mlkitdemo.presentation.screens.textTranslation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mlkitdemo.domain.useCases.GetTranslationUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class  TextTranslationViewState(
    val text: String = "",
    val translation: String = "",
    val isLoading: Boolean = false
)

class TextTranslationViewModel(private val useCase: GetTranslationUseCase): ViewModel() {
    private val _state = MutableStateFlow(TextTranslationViewState())
    val state: StateFlow<TextTranslationViewState> = _state.asStateFlow()

    fun updateText(text: String) {
        _state.update { it.copy(text = text) }
    }

    fun getLanguage() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = useCase(_state.value.text)
            _state.update { it.copy(translation = result, isLoading = false) }
        }
    }
}
package com.example.mlkitdemo.presentation.screens.textTranslation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mlkitdemo.R
import com.example.mlkitdemo.presentation.components.LoadingOverlay
import org.koin.androidx.compose.koinViewModel

@Composable
fun TextTranslationScreen() {
    val viewModel = koinViewModel<TextTranslationViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
            ) {
                Text(
                    stringResource(R.string.text_translation_from_spanish_to_english),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = state.text,
                    onValueChange = { viewModel.updateText(it) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { viewModel.getLanguage() }) {
                    Text(stringResource(R.string.translate))
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column {
                    Text(
                        text = state.translation,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            }
            if (state.isLoading) {
                LoadingOverlay()
            }
        }
    }
}
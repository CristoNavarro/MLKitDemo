package com.example.mlkitdemo.presentation.screens.objectDetection

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mlkitdemo.R
import com.example.mlkitdemo.presentation.components.CameraPreview
import com.example.mlkitdemo.presentation.screens.objectDetection.components.CameraOverlay
import org.koin.androidx.compose.koinViewModel

@Composable
fun ObjectDetectionScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current

    val viewModel = koinViewModel<ObjectDetectionViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    stringResource(R.string.object_detection),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                CameraOverlay(results = state.results) {
                    CameraPreview(
                        modifier = Modifier.fillMaxSize(),
                        lifecycleOwner = lifecycleOwner,
                        analyzer = state.analyzer
                    )
                }
            }
        }
    }
}
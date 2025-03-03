package com.example.mlkitdemo.presentation.screens.imageLabeling

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mlkitdemo.R
import com.example.mlkitdemo.presentation.components.CameraPreview
import com.example.mlkitdemo.presentation.components.LoadingOverlay
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageLabelingScreen() {
    val lifecycleOwner = LocalLifecycleOwner.current

    val viewModel = koinViewModel<ImageLabelingViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    LaunchedEffect(state.imageLabels) {
        if (state.imageLabels.isNotEmpty()) {
            viewModel.showBottomSheet()
        }
    }

    Surface {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column {
                Text(
                    stringResource(R.string.image_labeling),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                CameraPreview(
                    modifier = Modifier.fillMaxSize(),
                    lifecycleOwner = lifecycleOwner
                ) { imageProxy ->
                    imageProxy?.let { viewModel.analyzeImage(it) }
                }
                if (state.showBottomSheet) {
                    ModalBottomSheet(modifier = Modifier.fillMaxHeight(),
                        sheetState = sheetState,
                        onDismissRequest = { viewModel.hideBottomSheet() }) {
                        Column(
                            modifier = Modifier
                                .padding(20.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            state.imageLabels.forEachIndexed { index, label ->
                                Text(
                                    text = "${index + 1}. ${label.text} - ${label.confidence}",
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }
                        }

                    }
                }
            }
            if (state.isLoading) {
                LoadingOverlay()
            }
        }
    }
}

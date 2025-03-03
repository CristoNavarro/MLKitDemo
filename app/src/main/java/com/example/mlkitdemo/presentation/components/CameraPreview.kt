package com.example.mlkitdemo.presentation.components

import androidx.camera.core.ImageAnalysis.Analyzer
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.mlkitdemo.R
import java.util.concurrent.Executors

@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    analyzer: Analyzer? = null,
    onCaptureImage: ((ImageProxy?) -> Unit)? = null
) {
    val context = LocalContext.current
    val executor = remember { Executors.newSingleThreadExecutor() }

    val cameraController = remember {
        LifecycleCameraController(context)
    }

    LaunchedEffect(analyzer) {
        analyzer?.let {
            cameraController.setImageAnalysisAnalyzer(ContextCompat.getMainExecutor(context), analyzer)
        }
    }

    LaunchedEffect(lifecycleOwner) {
        cameraController.unbind()
        cameraController.apply {
            bindToLifecycle(lifecycleOwner)
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        AndroidView(
            modifier = modifier,
            factory = { ctx ->
                PreviewView(ctx).apply {
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    controller = cameraController
                }
            }
        )
        onCaptureImage?.let { onCaptureImage ->
            IconButton(
                onClick = {
                    cameraController.takePicture(executor, object : OnImageCapturedCallback() {
                        override fun onCaptureSuccess(image: ImageProxy) {
                            onCaptureImage(image)
                        }
                    })
                },
                modifier = Modifier
                    .padding(bottom = 20.dp)
                    .size(64.dp)
                    .border(2.dp, MaterialTheme.colorScheme.inversePrimary, CircleShape)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_circle_24),
                    contentDescription = "",
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
package com.example.mlkitdemo

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.mlkitdemo.di.startDI
import com.example.mlkitdemo.navigation.DrawerNavigation
import com.example.mlkitdemo.ui.theme.MLKitDemoTheme
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

class MainActivity : ComponentActivity() {

    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                setContent()
            } else {
                // Camera permission denied (Handle denied operation)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startDI {
            androidContext(this@MainActivity)
        }
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) -> {
                setContent()
            }
            else -> {
                cameraPermissionRequest.launch(android.Manifest.permission.CAMERA)
            }
        }

        enableEdgeToEdge()
    }

    @OptIn(KoinExperimentalAPI::class)
    private fun setContent() {
        setContent {
            MLKitDemoTheme {
                KoinAndroidContext {
                    DrawerNavigation()
                }
            }
        }
    }
}

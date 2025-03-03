package com.example.mlkitdemo.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mlkitdemo.R
import com.example.mlkitdemo.presentation.screens.home.HomeScreen
import com.example.mlkitdemo.presentation.screens.imageLabeling.ImageLabelingScreen
import com.example.mlkitdemo.presentation.screens.languageRecognition.LanguageRecognitionScreen
import com.example.mlkitdemo.presentation.screens.objectDetection.ObjectDetectionScreen
import com.example.mlkitdemo.presentation.screens.textFromCamera.TextFromCameraScreen
import com.example.mlkitdemo.presentation.screens.textFromImage.TextFromImageScreen
import com.example.mlkitdemo.presentation.screens.textTranslation.TextTranslationScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerNavigation() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: Screens.Home.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.use_cases),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 16.dp)
                    )
                    HorizontalDivider()
                    Text(
                        text= stringResource(R.string.text_recognition),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.from_image)) },
                        selected = currentRoute == Screens.TextFromImage.route,
                        onClick = {
                            navController.navigate(Screens.TextFromImage.route) {
                                launchSingleTop = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.from_camera)) },
                        selected = currentRoute == Screens.TextFromCamera.route,
                        onClick = {
                            navController.navigate(Screens.TextFromCamera.route) {
                                launchSingleTop = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = stringResource(R.string.image_recognition),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.labeling)) },
                        selected = currentRoute == Screens.ImageLabeling.route,
                        onClick = {
                            navController.navigate(Screens.ImageLabeling.route) {
                                launchSingleTop = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.object_detection)) },
                        selected = currentRoute == Screens.ObjectDetection.route,
                        onClick = {
                            navController.navigate(Screens.ObjectDetection.route) {
                                launchSingleTop = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = stringResource(R.string.natural_language),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.language_recognition)) },
                        selected = currentRoute == Screens.LanguageRecognition.route,
                        onClick = {
                            navController.navigate(Screens.LanguageRecognition.route) {
                                launchSingleTop = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text(stringResource(R.string.translation)) },
                        selected = currentRoute == Screens.TextTranslation.route,
                        onClick = {
                            navController.navigate(Screens.TextTranslation.route) {
                                launchSingleTop = true
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        }
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.mlkit_demo)) },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Filled.Menu, contentDescription = "")
                        }
                    }
                )
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screens.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Screens.Home.route) {
                    HomeScreen()
                }
                composable(Screens.TextFromImage.route) {
                    TextFromImageScreen()
                }
                composable(Screens.TextFromCamera.route) {
                    TextFromCameraScreen()
                }
                composable(Screens.ImageLabeling.route) {
                    ImageLabelingScreen()
                }
                composable(Screens.LanguageRecognition.route) {
                    LanguageRecognitionScreen()
                }
                composable(Screens.TextTranslation.route) {
                    TextTranslationScreen()
                }
                composable(Screens.ObjectDetection.route) {
                    ObjectDetectionScreen()
                }
            }
        }
    }
}
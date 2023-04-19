package com.example.metgalleryaf.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.metgalleryaf.data.AppContainer
import com.example.metgalleryaf.navigation.MetGalleryNavHost
import com.example.metgalleryaf.ui.theme.METGalleryAFTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetGalleryApp(appContainer: AppContainer) {

    METGalleryAFTheme {
        val navController = rememberNavController()
        Scaffold(
            //topBar = {TopAppBar(title = { Text(text = "Gallery")})},
            content = {MetGalleryNavHost(appContainer = appContainer, navController = navController)}
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyScaffold() = Scaffold(
    topBar = {TopAppBar(title = { Text(text = "SGallery")})},
    content = { Text(text = "Text")}
)
package com.example.metgalleryaf.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            topBar = {
                CenterAlignedTopAppBar(title = { Text(text = "Gallery") },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Return"
                        )
                    })
            },
            content = {
                MetGalleryNavHost(
                    modifier = Modifier.padding(it),
                    appContainer = appContainer,
                    navController = navController
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MyScaffold() = Scaffold(
    topBar = { TopAppBar(title = { Text(text = "SGallery") }) },
    content = { Text(text = "Text") }
)
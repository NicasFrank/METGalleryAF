package com.example.metgalleryaf.ui

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.metgalleryaf.data.AppContainer
import com.example.metgalleryaf.navigation.MetGalleryNavHost
import com.example.metgalleryaf.ui.theme.METGalleryAFTheme

@Composable
fun MetGalleryApp(appContainer: AppContainer) {

    METGalleryAFTheme {
        val navController = rememberNavController()
        Scaffold() {
            MetGalleryNavHost(navController = navController)
        }
    }

}
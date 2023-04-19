package com.example.metgalleryaf.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.metgalleryaf.data.AppContainer
import com.example.metgalleryaf.navigation.ItemDestination
import com.example.metgalleryaf.navigation.MetGalleryNavHost
import com.example.metgalleryaf.ui.theme.METGalleryAFTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetGalleryApp(appContainer: AppContainer) {

    METGalleryAFTheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val isItemScreen = currentBackStack?.destination?.route == ItemDestination.routeWithArgs
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = if (isItemScreen) ""
                            else "Gallery"
                        )
                    },
                    navigationIcon = {
                        if (isItemScreen) {
                            IconButton(onClick = { navController.popBackStack() }, content = {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Return"
                                )
                            })
                        } else {
                        }
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

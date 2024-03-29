package com.example.metgalleryaf.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.metgalleryaf.R
import com.example.metgalleryaf.navigation.ItemDestination
import com.example.metgalleryaf.navigation.MetGalleryNavHost
import com.example.metgalleryaf.ui.theme.METGalleryAFTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MetGalleryApp(
    navController: NavHostController = rememberNavController()
) {

    METGalleryAFTheme {
        val currentBackStack by navController.currentBackStackEntryAsState()
        val isItemScreen = currentBackStack?.destination?.route == ItemDestination.routeWithArgs
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = if (isItemScreen) currentBackStack?.arguments?.getInt(ItemDestination.itemIdArg).toString()
                            else stringResource(id = R.string.gallery_headline)
                        )
                    },
                    navigationIcon = {
                        if (isItemScreen) {
                            IconButton(onClick = { navController.popBackStack() }, content = {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = stringResource(id = R.string.navigate_back)
                                )
                            })
                        }
                    })
            },
            content = {
                MetGalleryNavHost(
                    modifier = Modifier.padding(it),
                    navController = navController
                )
            }
        )
    }
}

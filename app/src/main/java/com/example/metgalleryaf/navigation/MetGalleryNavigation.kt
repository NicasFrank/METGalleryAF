package com.example.metgalleryaf.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.metgalleryaf.data.AppContainer
import com.example.metgalleryaf.ui.gallery.GalleryScreen
import com.example.metgalleryaf.ui.gallery.GalleryViewModel
import com.example.metgalleryaf.ui.item.ItemScreen

interface MetGalleryDestinations{
    val route: String
}

object GalleryDestination: MetGalleryDestinations{
    override val route = "gallery"
}

object ItemDestination: MetGalleryDestinations{
    override val route = "item"
}

@Composable
fun MetGalleryNavHost(
    appContainer: AppContainer,
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = GalleryDestination.route
    ){
        composable(route = GalleryDestination.route){
            val galleryViewModel: GalleryViewModel = viewModel(
                factory = GalleryViewModel.provideFactory(appContainer.galleryRepository)
            )
            GalleryScreen(
                galleryViewModel
            )
            { navController.navigateSingleTopTo(ItemDestination.route) }
        }
        composable(route = ItemDestination.route){
            ItemScreen {

            }
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id)
        launchSingleTop = true
    }
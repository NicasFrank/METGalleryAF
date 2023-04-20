package com.example.metgalleryaf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.metgalleryaf.data.AppContainer
import com.example.metgalleryaf.ui.gallery.GalleryScreen
import com.example.metgalleryaf.ui.gallery.GalleryViewModel
import com.example.metgalleryaf.ui.item.ItemScreen
import com.example.metgalleryaf.ui.item.ItemViewModel

interface MetGalleryDestinations {
    val route: String
}

object GalleryDestination : MetGalleryDestinations {
    override val route = "gallery"
}

object ItemDestination : MetGalleryDestinations {
    override val route = "item"
    const val itemIdArg = "itemId"
    val routeWithArgs = "$route/{$itemIdArg}"
    val arguments = listOf(navArgument("itemId") { type = NavType.IntType })
}

@Composable
fun MetGalleryNavHost(
    modifier: Modifier = Modifier,
    appContainer: AppContainer,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = GalleryDestination.route
    ) {
        composable(route = GalleryDestination.route) {
            val galleryViewModel: GalleryViewModel = viewModel(
                factory = GalleryViewModel.provideFactory(appContainer.galleryRepository)
            )
            GalleryScreen(
                galleryViewModel
            ) { itemId -> navController.navigateToItem(itemId) }
        }
        composable(
            route = ItemDestination.routeWithArgs,
            arguments = ItemDestination.arguments
        ) {
            val itemViewModel: ItemViewModel = viewModel(
                factory = ItemViewModel.provideFactory(
                    appContainer.galleryRepository,
                    it.arguments?.getInt(ItemDestination.itemIdArg)
                )
            )
            ItemScreen(itemViewModel)
        }
    }
}

fun NavHostController.navigateToItem(objectId: Int) {
    this.navigate("item/$objectId") {
        popUpTo(this@navigateToItem.graph.findStartDestination().id)
        launchSingleTop = true
    }
}
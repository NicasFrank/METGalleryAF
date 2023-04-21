package com.example.metgalleryaf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
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
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = GalleryDestination.route
    ) {
        composable(route = GalleryDestination.route) {
            val galleryViewModel = hiltViewModel<GalleryViewModel>()
            GalleryScreen(galleryViewModel,
            ) { itemId -> navController.navigateToItem(itemId) }
        }
        composable(
            route = ItemDestination.routeWithArgs,
            arguments = ItemDestination.arguments
        ) {
            val itemViewModel = hiltViewModel<ItemViewModel>()
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
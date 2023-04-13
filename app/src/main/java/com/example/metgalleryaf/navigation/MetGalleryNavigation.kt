package com.example.metgalleryaf.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.metgalleryaf.ui.gallery.GalleryScreen
import com.example.metgalleryaf.ui.item.ItemScreen

interface MetGalleryDestinations{
    val route: String
}

object Gallery: MetGalleryDestinations{
    override val route = "gallery"
}

object Item: MetGalleryDestinations{
    override val route = "item"
}

@Composable
fun MetGalleryNavHost(
    navController: NavHostController
){
    NavHost(
        navController = navController,
        startDestination = Gallery.route
    ){
        composable(route = Gallery.route){
            GalleryScreen(){
                navController.navigateSingleTopTo(Item.route)
            }
        }
        composable(route = Item.route){
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
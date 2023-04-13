package com.example.metgalleryaf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.metgalleryaf.ui.gallery.GalleryScreen

object MetGalleryDestinations{
    const val GALLERY_ROUTE = "gallery"
    const val ITEM_ROUTE = "item"
}

@Composable
fun MetGalleryNavHost(
    navController: NavHostController,
    modifier: Modifier
){
    NavHost(
        navController = navController,
        startDestination = MetGalleryDestinations.GALLERY_ROUTE,
        modifier = modifier
    ){
        composable(route = MetGalleryDestinations.GALLERY_ROUTE){
            GalleryScreen(){
                navController.navigate(MetGalleryDestinations.ITEM_ROUTE){
                    launchSingleTop = true
                }
            }
        }
        composable(route = MetGalleryDestinations.ITEM_ROUTE){
        }
    }
}
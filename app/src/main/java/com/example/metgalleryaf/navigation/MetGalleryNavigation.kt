package com.example.metgalleryaf.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

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

        }
        composable(route = MetGalleryDestinations.ITEM_ROUTE){

        }
    }
}
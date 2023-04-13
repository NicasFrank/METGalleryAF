package com.example.metgalleryaf.data

import android.content.Context

interface AppContainer{
    val galleryRepository: GalleryRepository
}

class AppContainerImpl(private val applicationContext: Context): AppContainer {

    override val galleryRepository: GalleryRepository by lazy {
        GalleryRepository()
    }

}
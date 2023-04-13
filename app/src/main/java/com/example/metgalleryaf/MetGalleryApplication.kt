package com.example.metgalleryaf

import android.app.Application
import com.example.metgalleryaf.data.AppContainer
import com.example.metgalleryaf.data.AppContainerImpl

class MetGalleryApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainerImpl(this)
    }

}
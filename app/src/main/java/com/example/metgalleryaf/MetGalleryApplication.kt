package com.example.metgalleryaf

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MetGalleryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }

}
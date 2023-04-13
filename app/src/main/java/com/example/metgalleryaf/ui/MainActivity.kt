package com.example.metgalleryaf.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.metgalleryaf.MetGalleryApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = (application as MetGalleryApplication).container
        setContent {
            MetGalleryApp(appContainer = appContainer)
        }
    }
}
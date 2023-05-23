package com.example.metgalleryaf.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceModifier
import androidx.glance.background
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text

@Composable
fun MetGalleryWidget() {

    Box(modifier = GlanceModifier.fillMaxSize().background(Color.Blue)) {
        Text(text = "PPEPDEDE")
    }

}
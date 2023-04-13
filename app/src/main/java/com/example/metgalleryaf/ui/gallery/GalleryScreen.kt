package com.example.metgalleryaf.ui.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun GalleryScreen(onClickItem: () -> Unit) {
    Column {
        Text(text = "This is the Gallery Screen!")
        Button(onClick = onClickItem) {
            
        }
    }
}
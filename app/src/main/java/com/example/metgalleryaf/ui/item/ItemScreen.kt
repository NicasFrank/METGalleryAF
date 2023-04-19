package com.example.metgalleryaf.ui.item

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


@Composable
fun ItemScreen(
    onClickBack: () -> Unit,
    itemId: Int?
) {
    Text(text = "This is the ItemScreen for $itemId!")
}
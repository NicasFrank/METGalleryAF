package com.example.metgalleryaf.ui.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel,
    onClickItem: () -> Unit
) {

    val uiState by galleryViewModel.uiState.collectAsStateWithLifecycle()

    Column {
        SearchBar(searchInput = uiState.query, onSearchInputChanged = {galleryViewModel.onSearchInputChanged(it)})
        Button(onClick = onClickItem){}
    }
}

@Composable
fun SearchBar(modifier: Modifier = Modifier, searchInput: String = "", onSearchInputChanged: (String)->Unit){
    TextField(
        value = searchInput,
        onValueChange = onSearchInputChanged,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(
                min = 56.dp
            )
    )
}
package com.example.metgalleryaf.ui.gallery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.metgalleryaf.model.Item

@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel,
    onClickItem: () -> Unit
) {

    val uiState by galleryViewModel.uiState.collectAsStateWithLifecycle()

    Column {
        SearchBar(searchInput = uiState.query, onSearchInputChanged = {galleryViewModel.onSearchInputChanged(it)})
        Button(onClick = {
            galleryViewModel.searchForItems(uiState.query)
        }){}
        ItemGallery(itemList = uiState.matchingItems)
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

@Composable
fun ItemGallery(
    modifier: Modifier = Modifier,
    itemList: List<Item> = listOf()
){
    LazyColumn(modifier = modifier){
        items(itemList){
            item -> ItemElement(text = item.title)
        }
    }
}

@Composable
fun ItemElement(
    text: String,
    modifier:Modifier = Modifier
){
    Text(
        text = text
    )
}
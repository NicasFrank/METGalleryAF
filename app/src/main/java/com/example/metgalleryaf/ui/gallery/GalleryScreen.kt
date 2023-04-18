package com.example.metgalleryaf.ui.gallery

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        SearchBar(modifier = Modifier, onSearchInputChanged = {galleryViewModel.onSearchInputChanged(it)})
        Button(
            onClick = {galleryViewModel.searchForItems(uiState.query)}
        ) {}
        ItemGallery(itemList = uiState.matchingItems)
    }
}

@Composable
fun SearchSettings(
    modifier: Modifier = Modifier,
    onSearchInputChanged: (String)->Unit,
    onSearchButtonClick: ()->Unit
){
    Box(modifier.fillMaxWidth()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier) {
            SearchBar(modifier = modifier, onSearchInputChanged = onSearchInputChanged)
            Button(
                onClick = onSearchButtonClick
            ) {}
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier,
    searchInput: String = "",
    onSearchInputChanged: (String)->Unit
){
    TextField(
        value = searchInput,
        onValueChange = onSearchInputChanged,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        modifier = modifier
            .heightIn(
                min = 56.dp
            )
            .clip(CircleShape)
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
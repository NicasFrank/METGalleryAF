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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.metgalleryaf.model.Item

@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel,
    onClickItem: () -> Unit
) {

    val uiState by galleryViewModel.uiState.collectAsStateWithLifecycle()

    Column() {
        SearchSettings(onSearchInputChanged = {galleryViewModel.onSearchInputChanged(it)},
            onSearchButtonClick = {galleryViewModel.searchForItems(uiState.searchParameters.query)},
            onHighlightCheck = {galleryViewModel.onHighlightCheck(uiState.searchParameters.onlyHighlights)},
            searchParameters = uiState.searchParameters
        )
        ItemGallery(itemList = uiState.matchingItems)
    }
}

@Composable
fun SearchSettings(
    onSearchInputChanged: (String)->Unit,
    onSearchButtonClick: ()->Unit,
    onHighlightCheck: (Boolean)->Unit,
    searchParameters: SearchParameters
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(onSearchInputChanged = onSearchInputChanged, query = searchParameters.query)
        Row(horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = searchParameters.onlyHighlights, onCheckedChange = onHighlightCheck)
            Text(text = "Display only Highlights")
        }
        Button(
            onClick = onSearchButtonClick,
            modifier = Modifier
                .widthIn(min = 100.dp)
                .heightIn(min = 45.dp)
        ) {}
    }
}

@Preview
@Composable
fun SearchSettingsPreview() = SearchSettings(
    onSearchInputChanged = {},
    onSearchButtonClick = {},
    onHighlightCheck = {},
    searchParameters = SearchParameters("", false)
)

@Composable
fun SearchBar(
    query: String = "",
    onSearchInputChanged: (String)->Unit
){
    TextField(
        value = query,
        onValueChange = onSearchInputChanged,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 10.dp)
            .heightIn(min = 56.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ItemGallery(
    itemList: List<Item> = listOf()
){
    LazyColumn(){
        items(itemList){
            item -> ItemElement(text = item.title)
        }
    }
}

@Composable
fun ItemElement(
    text: String
){
    Text(
        text = text
    )
}
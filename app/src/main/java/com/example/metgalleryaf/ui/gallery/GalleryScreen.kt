package com.example.metgalleryaf.ui.gallery

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.metgalleryaf.model.Item

@Composable
fun GalleryScreen(
    galleryViewModel: GalleryViewModel,
    onClickItem: (Int) -> Unit
) {

    val uiState by galleryViewModel.uiState.collectAsStateWithLifecycle()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        SearchSettings(
            onSearchInputChanged = { galleryViewModel.onSearchInputChanged(it) },
            onHighlightCheck = { galleryViewModel.onHighlightCheck() },
            searchParameters = uiState.searchParameters
        )
        FilledIconButton(
            onClick = { galleryViewModel.searchForItems() },
            modifier = Modifier
                .widthIn(min = 80.dp)
                .heightIn(min = 40.dp),
            content = {
                if (uiState.isLoading)
                    CircularProgressIndicator()
                else
                    Text(text = "Search")

            },
            enabled = !uiState.isLoading
        )
        ItemGallery(itemList = uiState.matchingItems, onClickItem = onClickItem)
    }
}

@Composable
fun SearchSettings(
    onSearchInputChanged: (String) -> Unit,
    onHighlightCheck: (Boolean) -> Unit,
    searchParameters: SearchParameters
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(onSearchInputChanged = onSearchInputChanged, query = searchParameters.query)
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = searchParameters.onlyHighlights, onCheckedChange = onHighlightCheck)
            Text(text = "Display only Highlights")
        }
    }
}

@Preview
@Composable
fun SearchSettingsPreview() = SearchSettings(
    onSearchInputChanged = {},
    onHighlightCheck = {},
    searchParameters = SearchParameters("", false)
)

@Composable
fun SearchBar(
    query: String = "",
    onSearchInputChanged: (String) -> Unit
) {
    TextField(
        value = query,
        onValueChange = onSearchInputChanged,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, top = 5.dp, end = 10.dp)
            .heightIn(min = 56.dp)
            .clip(CircleShape)
    )
}

@Composable
fun ItemGallery(
    itemList: List<Item> = listOf(),
    onClickItem: (Int) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(itemList) { item ->
            ItemElement(
                itemId = item.objectID,
                title = item.title,
                item.primaryImageSmall,
                onClickItem = onClickItem
            )
        }
    }
}

@Composable
fun ItemElement(
    itemId: Int,
    title: String,
    previewImg: String,
    onClickItem: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickItem(itemId) },
        content = {
            Row(
                modifier = Modifier.padding(10.dp)
            ) {
                SubcomposeAsyncImage(
                    model = previewImg,
                    contentDescription = "Preview Image",
                    modifier = Modifier.size(width = 100.dp, height = 100.dp)
                ) {
                    when (painter.state) {
                        is AsyncImagePainter.State.Loading ->
                            CircularProgressIndicator()
                        is AsyncImagePainter.State.Error ->
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "No Image Available"
                            )
                        is AsyncImagePainter.State.Empty ->
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = "No Image Available"
                            )
                        is AsyncImagePainter.State.Success ->
                            SubcomposeAsyncImageContent()
                    }
                }
                Text(
                    text = title
                )
            }
        }
    )
}

@Preview
@Composable
fun ItemElementPreview() = ItemElement(
    itemId = 1,
    title = "Mona Lisa",
    previewImg = "https://images.metmuseum.org/CRDImages/ep/web-large/DP159891.jpg"
) {}
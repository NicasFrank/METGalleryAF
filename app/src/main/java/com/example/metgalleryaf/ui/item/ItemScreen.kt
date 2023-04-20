package com.example.metgalleryaf.ui.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent


@Composable
fun ItemScreen(
    itemViewModel: ItemViewModel
) {
    Column() {
        Row() {
            SubcomposeAsyncImage(
                model = itemViewModel.item?.primaryImage,
                contentDescription = "Image",
                modifier = Modifier.weight(1f)
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
            Column(modifier = Modifier.weight(2f)) {
                Text(text = itemViewModel.item?.title ?: "")
                Text(text = itemViewModel.item?.artistDisplayName ?: "")
                Text(text = itemViewModel.item?.objectDate ?: "")
            }
        }
        if(itemViewModel.item?.additionalImages?.isNotEmpty() == true){
            val images = itemViewModel.item?.additionalImages ?: listOf()
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                items(images){ image ->
                    SubcomposeAsyncImage(
                        model = image,
                        contentDescription = "Additional Image"
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
                }
            }
        }
    }
}
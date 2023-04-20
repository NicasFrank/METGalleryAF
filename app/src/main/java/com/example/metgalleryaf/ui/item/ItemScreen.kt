package com.example.metgalleryaf.ui.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.metgalleryaf.R


@Composable
fun ItemScreen(itemViewModel: ItemViewModel) {
    Column() {
        val item = itemViewModel.item
        Row() {
            SubcomposeAsyncImage(
                model = item?.primaryImage,
                contentDescription = stringResource(id = R.string.main_image),
                modifier = Modifier.weight(1f)
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Loading ->
                        CircularProgressIndicator()
                    is AsyncImagePainter.State.Error ->
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = stringResource(id = R.string.image_error)
                        )
                    is AsyncImagePainter.State.Empty ->
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = stringResource(id = R.string.image_error)
                        )
                    is AsyncImagePainter.State.Success ->
                        SubcomposeAsyncImageContent()
                }
            }
            Column(modifier = Modifier.weight(2f)) {
                Text(text = item?.title ?: "")
                Text(text = item?.artistDisplayName ?: "")
                Text(text = item?.objectDate ?: "")
            }
        }
        if (item?.additionalImages?.isNotEmpty() == true) {
            val images = item.additionalImages
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                items(images) { image ->
                    SubcomposeAsyncImage(
                        model = image,
                        contentDescription = stringResource(id = R.string.additional_image),
                        modifier = Modifier.size(width = 128.dp, height = 128.dp)
                    ) {
                        when (painter.state) {
                            is AsyncImagePainter.State.Loading ->
                                CircularProgressIndicator()
                            is AsyncImagePainter.State.Error ->
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = stringResource(id = R.string.image_error)
                                )
                            is AsyncImagePainter.State.Empty ->
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = stringResource(id = R.string.image_error)
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
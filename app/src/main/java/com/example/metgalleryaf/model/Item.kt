package com.example.metgalleryaf.model

import com.example.metgalleryaf.data.database.DatabaseItem

data class Item(
    val objectID: Int,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String
)

fun Item.asDatabaseModel(): DatabaseItem {
    return DatabaseItem(
        objectID = objectID,
        primaryImage = primaryImage,
        primaryImageSmall = primaryImageSmall,
        additionalImages = additionalImages,
        title = title,
        artistDisplayName = artistDisplayName,
        objectDate = objectDate
    )
}
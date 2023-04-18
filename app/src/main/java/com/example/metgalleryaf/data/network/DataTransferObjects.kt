package com.example.metgalleryaf.data.network

import com.example.metgalleryaf.data.database.DatabaseItem
import com.example.metgalleryaf.model.Item
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ItemIdList(
    val objectIDs: List<Int>
)

@JsonClass(generateAdapter = true) data class NetworkItem(
    val objectID: Int,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String
)


fun NetworkItem.asDomainModel(): Item{
    return Item(
        objectID = objectID,
        primaryImage = primaryImage,
        primaryImageSmall = primaryImageSmall,
        additionalImages = additionalImages,
        title = title,
        artistDisplayName = artistDisplayName,
        objectDate = objectDate
    )
}

fun NetworkItem.asDatabaseModel(): DatabaseItem{
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
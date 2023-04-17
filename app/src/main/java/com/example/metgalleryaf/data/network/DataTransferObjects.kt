package com.example.metgalleryaf.data.network

import com.example.metgalleryaf.data.database.DatabaseItem
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class HighlightIdList(
    val total: Int,
    val objectIDs: List<Int>
)

@JsonClass(generateAdapter = true)
data class ItemIdList(
    val total: Int,
    val idList: List<Int>
)

@JsonClass(generateAdapter = true)
data class Item(
    val objectID: Int,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String
)


fun Item.asDatabaseModel(): DatabaseItem{
    return DatabaseItem(
        objectId = objectID,
        title = title
    )
}
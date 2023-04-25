package com.example.metgalleryaf.data.network

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ItemIdList(
    val objectIDs: List<Int>
)

@JsonClass(generateAdapter = true) data class NetworkItem(
    val objectID: Int,
    val isHighlight: Boolean,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String
)
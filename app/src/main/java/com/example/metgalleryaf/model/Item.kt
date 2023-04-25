package com.example.metgalleryaf.model

data class Item(
    val objectID: Int,
    val isHighlight: Boolean,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String
)
package com.example.metgalleryaf.model

import com.example.metgalleryaf.data.database.DatabaseItem
import com.example.metgalleryaf.data.network.NetworkItem

interface Mapper<From, To> {

    fun mapTo(item: From): To
    fun mapFrom(item: To): From

}

object DatabaseMapper: Mapper<DatabaseItem, Item>{
    override fun mapTo(item: DatabaseItem): Item {
        return Item(
            objectID = item.objectID,
            isHighlight = item.isHighlight,
            primaryImage = item.primaryImage,
            primaryImageSmall = item.primaryImageSmall,
            additionalImages = item.additionalImages,
            title = item.title,
            artistDisplayName = item.artistDisplayName,
            objectDate = item.objectDate
        )
    }

    override fun mapFrom(item: Item): DatabaseItem {
        return DatabaseItem(
            objectID = item.objectID,
            isHighlight = item.isHighlight,
            primaryImage = item.primaryImage,
            primaryImageSmall = item.primaryImageSmall,
            additionalImages = item.additionalImages,
            title = item.title,
            artistDisplayName = item.artistDisplayName,
            objectDate = item.objectDate
        )
    }

}

object NetworkMapper: Mapper<NetworkItem, Item>{
    override fun mapTo(item: NetworkItem): Item {
        return Item(
            objectID = item.objectID,
            isHighlight = item.isHighlight,
            primaryImage = item.primaryImage,
            primaryImageSmall = item.primaryImageSmall,
            additionalImages = item.additionalImages,
            title = item.title,
            artistDisplayName = item.artistDisplayName,
            objectDate = item.objectDate
        )
    }

    override fun mapFrom(item: Item): NetworkItem {
        return NetworkItem(
            objectID = item.objectID,
            isHighlight = item.isHighlight,
            primaryImage = item.primaryImage,
            primaryImageSmall = item.primaryImageSmall,
            additionalImages = item.additionalImages,
            title = item.title,
            artistDisplayName = item.artistDisplayName,
            objectDate = item.objectDate
        )
    }

}
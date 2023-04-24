package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.ItemDao
import com.example.metgalleryaf.data.database.itemAsDomainModel
import com.example.metgalleryaf.data.database.itemListAsDomainModel
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.data.network.asDatabaseModel
import com.example.metgalleryaf.model.Item
import javax.inject.Inject

class GalleryRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val network: MetNetwork
    ) {

    suspend fun fetchItems(query: String, onlyHighlights: Boolean): List<Item> {
        val result = network.fetchItems(query, onlyHighlights)
        if (result.isSuccess) {
            val foundItems = result.getOrNull()!!
            for (item in foundItems) {
                itemDao.insertItem(item.asDatabaseModel())
            }
        }
        return if (onlyHighlights) itemDao.getHighlights("%$query%").itemListAsDomainModel()
        else itemDao.getItems("%$query%").itemListAsDomainModel()
    }

    suspend fun fetchItemById(itemId: Int): Item? {
        return itemDao.getItemById(itemId)?.itemAsDomainModel()
    }

}
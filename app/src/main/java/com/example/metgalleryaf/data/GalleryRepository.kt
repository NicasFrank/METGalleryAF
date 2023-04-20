package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.GalleryRoomDB
import com.example.metgalleryaf.data.database.itemAsDomainModel
import com.example.metgalleryaf.data.database.itemListAsDomainModel
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.model.Item
import javax.inject.Inject

class GalleryRepository @Inject constructor(private val database: GalleryRoomDB) {

    suspend fun fetchItems(query: String, onlyHighlights: Boolean): List<Item> {
        val result = MetNetwork.fetchItems(query, onlyHighlights)
        if (result.isSuccess) {
            val foundItems = result.getOrNull()!!
            for (item in foundItems) {
                database.itemDao.insertItem(item)
            }
        }
        return if (onlyHighlights) database.itemDao.getHighlights("%$query%").itemListAsDomainModel()
        else database.itemDao.getItems("%$query%").itemListAsDomainModel()
    }

    suspend fun fetchItemById(itemId: Int): Item? {
        return database.itemDao.getItemById(itemId)?.itemAsDomainModel()
    }

}
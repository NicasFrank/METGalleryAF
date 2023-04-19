package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.GalleryRoomDB
import com.example.metgalleryaf.data.database.asDomainModel
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.model.Item

class GalleryRepository(private val database: GalleryRoomDB) {

    suspend fun fetchItems(query: String, onlyHighlights: Boolean): List<Item> {
        val result = MetNetwork.fetchItems(query, onlyHighlights)
        if (result.isSuccess) {
            val foundItems = result.getOrNull()!!
            for (item in foundItems) {
                database.itemDao.insertItem(item)
            }
        }
        return if (onlyHighlights) database.itemDao.getHighlights("%$query%").asDomainModel()
        else database.itemDao.getItems("%$query%").asDomainModel()
    }


}
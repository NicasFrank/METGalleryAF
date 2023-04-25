package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.ItemDao
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.model.DatabaseMapper
import com.example.metgalleryaf.model.Item
import com.example.metgalleryaf.model.NetworkMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GalleryRepository @Inject constructor(
    private val itemDao: ItemDao,
    private val network: MetNetwork
) {

    suspend fun fetchItems(query: String, onlyHighlights: Boolean): List<Item> {
        val result = network.fetchItems(query, onlyHighlights)
        if (result.isSuccess) {
            val foundItems = result.getOrNull()!!.map {
                NetworkMapper.mapTo(it)
            }
            for (item in foundItems) {
                itemDao.insertItem(DatabaseMapper.mapFrom(item))
            }
        }
        return if (onlyHighlights) itemDao.getHighlights("%$query%").map {
            DatabaseMapper.mapTo(it)
        }
        else itemDao.getItems("%$query%").map {
            DatabaseMapper.mapTo(it)
        }
    }

    suspend fun fetchItemById(itemId: Int): Item? {
        return itemDao.getItemById(itemId)?.let { DatabaseMapper.mapTo(it) }
    }

}
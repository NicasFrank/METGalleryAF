package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.GalleryRoomDB
import com.example.metgalleryaf.data.database.asDomainModel
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.model.Item
import com.example.metgalleryaf.model.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GalleryRepository(private val database: GalleryRoomDB) {

    suspend fun fetchItems(query: String, onlyHighlights: Boolean): Result<List<Item>> {
        return withContext(Dispatchers.IO){
            val result = MetNetwork.fetchItems(query, onlyHighlights)
            if(result.isSuccess){
                val foundItems = result.getOrNull()!!
                for(item in foundItems){
                    database.itemDao.insertItem(item.asDatabaseModel())
                }
            }
            Result.success(database.itemDao.getItems(query).asDomainModel())
        }
    }


}
package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.GalleryRoomDB
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.data.network.asDatabaseModel
import com.example.metgalleryaf.model.Item

class GalleryRepository(private val database: GalleryRoomDB) {


    suspend fun saveHighlights(){
        val highlightIdList = MetNetwork.metGallery.getHighlightIds()
        for(id in highlightIdList.objectIDs){
            database.itemDao.insertItem(MetNetwork.metGallery.getItem(id).asDatabaseModel())
        }
    }

    suspend fun findItems(query: String?): Result<List<Item>>{
        saveHighlights()
        /*return withContext(Dispatchers.IO){
            val items = database.itemDao.getItems().asDomainModel().filter { it.objectID.toString() == query }
            if(items.isEmpty()){
                Result.failure(IllegalAccessException("No items found"))
            }
            else{
                Result.success(items)
            }
        }*/
        return Result.failure(IllegalAccessException("No items found"))
    }

}
package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.GalleryRoomDB
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.data.network.asDatabaseModel
import com.example.metgalleryaf.data.network.asDomainModel
import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class GalleryRepository(private val database: GalleryRoomDB) {


    suspend fun saveHighlights(){
        val highlightIdList = MetNetwork.metGallery.getHighlightIds()
        for(id in highlightIdList.objectIDs){
            database.itemDao.insertItem(MetNetwork.metGallery.getItem(id).asDatabaseModel())
        }
    }

    suspend fun findItems(query: String): Result<List<Item>>{
        return withContext(Dispatchers.IO){
            val itemIds = MetNetwork.metGallery.searchForQuery(query)
            val items = mutableListOf<Item>()
            var idTest = 0;
            for(id in itemIds.objectIDs){
                idTest = id
                try {
                    items.add(MetNetwork.metGallery.getItem(id).asDomainModel())
                }
                catch (e: HttpException){
                    println(idTest)
                    continue
                }
            }
            if(items.isEmpty()){
                Result.failure(IllegalAccessException("No items found"))
            }
            else{
                Result.success(items)
            }
        }
    }

}
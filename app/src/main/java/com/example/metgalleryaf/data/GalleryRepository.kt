package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.DatabaseItem
import com.example.metgalleryaf.data.database.GalleryRoomDB
import com.example.metgalleryaf.data.database.asDomainModel
import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GalleryRepository(private val database: GalleryRoomDB) {

    private val dummyItems = listOf(
        Item(1, "Der"),
        Item(1, "Hse"),
        Item(3,"Ist"),
        Item(4, "weich"),
        Item(5, "und")
    )


    suspend fun findItems(query: String?): Result<List<Item>>{
        for(item in dummyItems){
            database.itemDao.insertItem(DatabaseItem(objectID = item.objectID, title = item.title))
        }
        return withContext(Dispatchers.IO){
            val items = database.itemDao.getItems().asDomainModel().filter { it.objectID.toString() == query }
            if(items.isEmpty()){
                Result.failure(IllegalAccessException("No items found"))
            }
            else{
                Result.success(items)
            }
        }
    }

}
package com.example.metgalleryaf.data

import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GalleryRepository {

    private val dummyItems = listOf(
        Item(1),
        Item(2),
        Item(3),
        Item(4),
        Item(5)
    )


    suspend fun findItems(query: String?): Result<List<Item>>{
        return withContext(Dispatchers.IO){
            val items = dummyItems.filter { it.objectID.toString() == query }
            if(items.isEmpty()){
                Result.failure(IllegalAccessException("No items found"))
            }
            else{
                Result.success(items)
            }
        }
    }

}
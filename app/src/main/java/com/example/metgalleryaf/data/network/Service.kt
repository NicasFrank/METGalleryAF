package com.example.metgalleryaf.data.network

import android.accounts.NetworkErrorException
import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MetService{

    @GET("search?")
    suspend fun searchForItem(@Query("q") query: String): ItemIdList

    @GET("search?isHighlight=true&")
    suspend fun searchForHighlight(@Query("q") query: String): ItemIdList

    @GET("objects/{id}")
    suspend fun getItem(@Path("id") id: Int): NetworkItem
}

object MetNetwork{

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://collectionapi.metmuseum.org/public/collection/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val metGallery: MetService = retrofit.create(MetService::class.java)

    suspend fun fetchItems(query: String, onlyHighlights: Boolean): Result<List<Item>>{
        return withContext(Dispatchers.IO){
            try {
                val itemIds =
                    if(onlyHighlights)
                        metGallery.searchForHighlight(query)
                    else
                        metGallery.searchForItem(query)
                val items = mutableListOf<Item>()
                var idTest: Int
                for(id in itemIds.objectIDs){
                    idTest = id
                    try {
                        items.add(metGallery.getItem(id).asDomainModel())
                    }
                    catch (e: HttpException){
                        println(idTest)
                        continue
                    }
                }
                if(items.isEmpty()){
                    Result.failure(IllegalArgumentException("No items found"))
                }
                else{
                    Result.success(items)
                }
            } catch (e:Exception){
                Result.failure(NetworkErrorException("Couldn't connect to Server"))
            }
        }
    }

}
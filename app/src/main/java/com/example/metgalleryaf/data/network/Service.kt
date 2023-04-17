package com.example.metgalleryaf.data.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MetService{
    @GET("search?q=&isHighlight=true")
    suspend fun getHighlightIds(): HighlightIdList

    @GET("search")
    suspend fun searchForQuery(@Query("q") query: String): ItemIdList

    @GET("objects/{id}")
    suspend fun getItem(@Path("id") id: Int): Item
}

object MetNetwork{

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://collectionapi.metmuseum.org/public/collection/v1/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val metGallery = retrofit.create(MetService::class.java)

}
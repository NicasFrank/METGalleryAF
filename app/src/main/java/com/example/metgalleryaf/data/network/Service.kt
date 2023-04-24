package com.example.metgalleryaf.data.network

import android.accounts.NetworkErrorException
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


interface MetService {

    @GET("search")
    suspend fun searchForItem(@Query("q") query: String): ItemIdList

    @GET("search")
    suspend fun searchForHighlight(@Query("q") query: String, @Query("isHighlight") isHighlight: String = "true"): ItemIdList

    @GET("objects/{id}")
    suspend fun getItem(@Path("id") id: Int): NetworkItem
}

@Singleton
class MetNetwork @Inject constructor() {

    private val okHTTPClient: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://collectionapi.metmuseum.org/public/collection/v1/")
        .client(okHTTPClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    private val metGallery: MetService = retrofit.create(MetService::class.java)

    suspend fun fetchItems(query: String, onlyHighlights: Boolean): Result<List<NetworkItem>> {
        try {
            val itemIds =
                if (onlyHighlights)
                    metGallery.searchForHighlight(query)
                else
                    metGallery.searchForItem(query)
            val items = mutableListOf<NetworkItem>()
            coroutineScope {
                itemIds.objectIDs.map {
                    async {
                        try {
                            items.add(metGallery.getItem(it))
                        } catch (e: HttpException) {
                            Timber.w("Item $it not found")
                        }
                    }
                }.awaitAll()
            }
            return if (items.isEmpty()) {
                Result.failure(IllegalArgumentException("No items found"))
            } else {
                Result.success(items)
            }
        } catch (e: Exception) {
            return Result.failure(NetworkErrorException("Couldn't connect to Server"))
        }
    }

}
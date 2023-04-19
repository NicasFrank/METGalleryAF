package com.example.metgalleryaf.data.database

import android.content.Context
import androidx.room.*
import com.example.metgalleryaf.model.Item
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter


@Entity
data class DatabaseItem constructor(
    @PrimaryKey
    val objectID: Int,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String
)

fun List<DatabaseItem>.asDomainModel(): List<Item>{
    return map {
        Item(
            objectID = it.objectID,
            primaryImage = it.primaryImage,
            primaryImageSmall = it.primaryImageSmall,
            additionalImages = it.additionalImages,
            title = it.title,
            artistDisplayName = it.artistDisplayName,
            objectDate = it.objectDate
        )
    }
}

@Dao
interface ItemDao{
    @Query("SELECT * FROM databaseitem WHERE title LIKE %:query%")
    suspend fun getItems(query: String): List<DatabaseItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: DatabaseItem)
}

@Database(entities = [DatabaseItem::class], version = 3)
@TypeConverters(Converters::class)
abstract class GalleryRoomDB: RoomDatabase() {
    abstract val itemDao: ItemDao
}

private lateinit var INSTANCE: GalleryRoomDB

fun getDatabase(context: Context): GalleryRoomDB{
    synchronized(GalleryRoomDB::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                GalleryRoomDB::class.java,
                "item_database"
                ).fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}

@OptIn(ExperimentalStdlibApi::class)
class Converters{
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter<List<String>>()

    @TypeConverter
    fun stringListToJson(list: List<String>): String{
        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun jsonToStringList(json: String): List<String>{
        return jsonAdapter.fromJson(json)?: listOf()
    }
}
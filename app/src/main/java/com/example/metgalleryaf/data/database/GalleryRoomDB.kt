package com.example.metgalleryaf.data.database

import android.content.Context
import androidx.room.*
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter


@Entity
data class DatabaseItem constructor(
    @PrimaryKey
    val objectID: Int,
    val isHighlight: Boolean,
    val primaryImage: String,
    val primaryImageSmall: String,
    val additionalImages: List<String>,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String
)


@Dao
interface ItemDao {

    @Query("Select * From databaseitem WHERE objectId = :objectId")
    suspend fun getItemById(objectId: Int): DatabaseItem?

    @Query("SELECT * FROM databaseitem WHERE title LIKE :query OR artistDisplayName LIKE :query OR objectDate LIKE :query")
    suspend fun getItems(query: String): List<DatabaseItem>

    @Query("SELECT * FROM databaseitem WHERE (title LIKE :query OR artistDisplayName LIKE :query OR objectDate LIKE :query) AND isHighlight")
    suspend fun getHighlights(query: String): List<DatabaseItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: DatabaseItem)
}

@Database(entities = [DatabaseItem::class], version = 4)
@TypeConverters(Converters::class)
abstract class GalleryRoomDB : RoomDatabase() {
    abstract val itemDao: ItemDao
}

fun getDatabase(context: Context): GalleryRoomDB {
    return Room.databaseBuilder(
        context.applicationContext,
        GalleryRoomDB::class.java,
        "item_database"
    ).fallbackToDestructiveMigration()
        .build()
}

@OptIn(ExperimentalStdlibApi::class)
class Converters {
    private val moshi: Moshi = Moshi.Builder().build()
    private val jsonAdapter: JsonAdapter<List<String>> = moshi.adapter()

    @TypeConverter
    fun stringListToJson(list: List<String>): String {
        return jsonAdapter.toJson(list)
    }

    @TypeConverter
    fun jsonToStringList(json: String): List<String> {
        return jsonAdapter.fromJson(json) ?: listOf()
    }
}
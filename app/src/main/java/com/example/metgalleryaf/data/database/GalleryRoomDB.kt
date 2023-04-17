package com.example.metgalleryaf.data.database

import android.content.Context
import androidx.room.*
import com.example.metgalleryaf.model.Item


@Entity
data class DatabaseItem constructor(
    @PrimaryKey
    val objectID: Int,
    val title: String
)

fun List<DatabaseItem>.asDomainModel(): List<Item>{
    return map {
        Item(
            objectID = it.objectID,
            title = it.title
        )
    }
}

@Dao
interface ItemDao{
    @Query("select * from databaseitem")
    suspend fun getItems(): List<DatabaseItem>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItem(item: DatabaseItem)
}

@Database(entities = [DatabaseItem::class], version = 1)
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
                ).build()
        }
    }
    return INSTANCE
}
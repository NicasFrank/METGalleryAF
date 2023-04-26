package com.example.metgalleryaf.data

import com.example.metgalleryaf.data.database.DatabaseItem
import com.example.metgalleryaf.data.database.ItemDao
import com.example.metgalleryaf.data.network.MetNetwork
import com.example.metgalleryaf.data.network.NetworkItem
import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class GalleryRepositoryTest {

    private val testItemDB = DatabaseItem(
        objectID = 1,
        isHighlight = false,
        title = "",
        objectDate = "",
        artistDisplayName = "",
        additionalImages = listOf(),
        primaryImage = "",
        primaryImageSmall = "",
    )

    private val testItemDB2 = DatabaseItem(
        objectID = 1,
        isHighlight = false,
        title = "asda",
        objectDate = "",
        artistDisplayName = "",
        additionalImages = listOf(),
        primaryImage = "",
        primaryImageSmall = "",
    )

    private val testItemNW = NetworkItem(
        objectID = 1,
        isHighlight = false,
        title = "",
        objectDate = "",
        artistDisplayName = "",
        additionalImages = listOf(),
        primaryImage = "",
        primaryImageSmall = "",
    )

    private val testItem = Item(
        objectID = 1,
        isHighlight = false,
        title = "",
        objectDate = "",
        artistDisplayName = "",
        additionalImages = listOf(),
        primaryImage = "",
        primaryImageSmall = "",
    )

    private lateinit var galleryRepository: GalleryRepository

    @Mock
    private lateinit var metNetwork: MetNetwork

    @Mock
    private lateinit var itemDao: ItemDao

    @Before
    fun setUp() {
        metNetwork = mock {
            onBlocking { fetchItems("", false) } doReturn Result.success(listOf(testItemNW))
        }
        itemDao = mock {
            onBlocking { insertItem(any(DatabaseItem::class.java)?:testItemDB2) } doAnswer {
                assertEquals(testItemDB, it.arguments[0])
            }
            onBlocking { getItems("%%") } doReturn listOf(testItemDB)
            onBlocking { getItemById(1) } doReturn testItemDB
        }
        galleryRepository = GalleryRepository(itemDao, metNetwork)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchItems() = runTest{
        assertEquals(listOf(testItem), galleryRepository.fetchItems("", false))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchItemById() = runTest {
        assertEquals(testItem, galleryRepository.fetchItemById(1))
    }
}
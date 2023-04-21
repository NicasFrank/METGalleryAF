package com.example.metgalleryaf.ui.gallery

import com.example.metgalleryaf.data.GalleryRepository
import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule constructor(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}


@RunWith(MockitoJUnitRunner::class)
class GalleryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testItem = Item(
        objectID = 1,
        title = "",
        objectDate = "",
        artistDisplayName = "",
        additionalImages = listOf(),
        primaryImage = "",
        primaryImageSmall = "",
    )

    private val testItemList = listOf(testItem)

    private lateinit var galleryViewModel: GalleryViewModel

    @Mock
    private lateinit var galleryRepository: GalleryRepository

    @Before
    fun setUp(){
        galleryRepository = mock {
            onBlocking { fetchItems("", false) } doReturn testItemList
        }
        galleryViewModel = GalleryViewModel(galleryRepository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun searchForItems() = runTest{
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            galleryViewModel.uiState.collect()
        }
        galleryViewModel.searchForItems()
        assertEquals(testItemList,galleryViewModel.uiState.value.matchingItems)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onSearchInputChanged() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            galleryViewModel.uiState.collect()
        }
        assertEquals("", galleryViewModel.uiState.value.searchParameters.query)
        galleryViewModel.onSearchInputChanged("test")
        assertEquals("test", galleryViewModel.uiState.value.searchParameters.query)
        galleryViewModel.onSearchInputChanged("")
        assertEquals("", galleryViewModel.uiState.value.searchParameters.query)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onHighlightCheck() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
            galleryViewModel.uiState.collect()
        }
        assertEquals(false, galleryViewModel.uiState.value.searchParameters.onlyHighlights)
        galleryViewModel.onHighlightCheck()
        assertEquals(true, galleryViewModel.uiState.value.searchParameters.onlyHighlights)
    }
}
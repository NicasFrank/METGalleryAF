package com.example.metgalleryaf.ui.gallery

import com.example.metgalleryaf.data.GalleryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

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


    @Mock
    private lateinit var galleryRepository: GalleryRepository

    private lateinit var galleryViewModel: GalleryViewModel

    @Before
    fun setUp(){
        MockitoAnnotations.openMocks(this)
        galleryViewModel = GalleryViewModel(galleryRepository)
    }

    @Test
    fun searchForItems(){

    }

    @Test
    fun onSearchInputChanged() {
        assertEquals("", galleryViewModel.uiState.value.searchParameters.query)
        galleryViewModel.onSearchInputChanged("test")
        assertEquals("test", galleryViewModel.uiState.value.searchParameters.query)
    }

    @Test
    fun onHighlightCheck() {
        assertEquals(false, galleryViewModel.uiState.value.searchParameters.onlyHighlights)
        galleryViewModel.onHighlightCheck()
        assertEquals(true, galleryViewModel.uiState.value.searchParameters.onlyHighlights)
    }
}
package com.example.metgalleryaf.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.metgalleryaf.data.GalleryRepository
import com.example.metgalleryaf.model.Item
import kotlinx.coroutines.launch

class ItemViewModel(
    private val galleryRepository: GalleryRepository,
    private val itemId: Int?
) : ViewModel() {

    private val initialItem: Item? = null
    var item by mutableStateOf(initialItem)

    init {
        if(itemId!=null){
            viewModelScope.launch {
                val newItem = galleryRepository.fetchItemById(itemId)
                item = newItem
            }
        }
    }

    companion object {
        fun provideFactory(
            galleryRepository: GalleryRepository,
            itemId: Int?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return ItemViewModel(galleryRepository, itemId) as T
                }
                throw IllegalArgumentException("Unable to construct ViewModel")
            }
        }
    }

}
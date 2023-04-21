package com.example.metgalleryaf.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.metgalleryaf.data.GalleryRepository
import com.example.metgalleryaf.model.Item
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
class ItemViewModel @AssistedInject constructor(
    private val galleryRepository: GalleryRepository,
    @Assisted private val itemId: Int?
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

    @AssistedFactory
    interface ItemViewModelFactory{
        fun create(itemId: Int?): ItemViewModel
    }

    companion object {
        fun provideFactory(
            assistedFactory: ItemViewModelFactory,
            itemId: Int?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return assistedFactory.create(itemId) as T
                }
                throw IllegalArgumentException("Unable to construct ViewModel")
            }
        }
    }

}
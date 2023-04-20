package com.example.metgalleryaf.ui.item

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.metgalleryaf.data.GalleryRepository

class ItemViewModel(
    private val galleryRepository: GalleryRepository,
    private val itemId: Int?
): ViewModel() {
    companion object{
        fun provideFactory(
            galleryRepository: GalleryRepository,
            itemId: Int?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if(modelClass.isAssignableFrom(ItemViewModel::class.java)){
                    @Suppress("UNCHECKED_CAST")
                    return ItemViewModel(galleryRepository, itemId) as T
                }
                throw IllegalArgumentException("Unable to construct ViewModel")
            }
        }
    }

}